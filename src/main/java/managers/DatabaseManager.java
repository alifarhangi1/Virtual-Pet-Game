package managers;

import data.Database;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.*;

public class DatabaseManager implements AutoCloseable {
    private static final String DATABASE_FILE = "db.json";
    private static final String BACKUP_FILE = "backup.json";
    private static final String BACKUP_DIRECTORY = "backups/";
    private Database database;
    private boolean hasUnsavedChanges;
    private static final int MAX_BACKUP_FILES = 5;

    public DatabaseManager() {
        hasUnsavedChanges = false;
        initializeBackupDirectory();
        loadDatabase();
    }

    private void initializeBackupDirectory() {
        File backupDirectory = new File(BACKUP_DIRECTORY);
        if (!backupDirectory.exists()) {
            backupDirectory.mkdirs();
        }
    }

    private void loadDatabase() {
        try {
            File file = new File(DATABASE_FILE);
            if (!file.exists()) {
                database = new Database();
                saveDatabase();
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            database = new JSONDeserializer<Database>().deserialize(json.toString(), Database.class);
        } catch (IOException e) {
            System.err.println("Error loading database: " + e.getMessage());
            // Try to restore from backup if main file is corrupted
            if (!restoreFromBackup()) {
                // If restore fails, create new database
                database = new Database();
            }
        }
    }

    private void saveDatabase() {
        try {
            createTimeStampedBackup();

            JSONSerializer serializer = new JSONSerializer().prettyPrint(true);
            String json = serializer.serialize(database);

            File tempFile = new File(DATABASE_FILE + "_tmp");
            FileWriter writer = new FileWriter(tempFile);
            writer.write(json);
            writer.close();

            File mainFile = new File(DATABASE_FILE);
            if (mainFile.exists()) {
                mainFile.delete();
            }
            tempFile.renameTo(mainFile);

            hasUnsavedChanges = false;
            database.updateLastModified();

            // Clean up old backups
            cleanupOldBackups();
        } catch (IOException e) {
            System.err.println("Error saving database: " + e.getMessage());
            restoreFromBackup();
        }
    }

    private void createTimeStampedBackup() {
        try {
            File currentFile = new File(DATABASE_FILE);
            File backupFile = null;
            if (!currentFile.exists()) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                backupFile = new File(BACKUP_DIRECTORY + "db_" + timestamp + "_backup");
            }

            FileInputStream fis = new FileInputStream(currentFile);
            FileOutputStream fos = new FileOutputStream(backupFile);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            fis.close();
            fos.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }

    private void cleanupOldBackups() {
        File backupDir = new File(BACKUP_DIRECTORY);
        File[] backups = backupDir.listFiles((dir, name) -> name.endsWith(".backup"));

        if (backups != null && backups.length > MAX_BACKUP_FILES) {
            // Sort by last modified time
            java.util.Arrays.sort(backups, (f1, f2) ->
                    Long.compare(f2.lastModified(), f1.lastModified()));

            // Delete oldest backups
            for (int i = MAX_BACKUP_FILES; i < backups.length; i++) {
                backups[i].delete();
            }
        }
    }

    private boolean restoreFromBackup() {
        File backupDir = new File(BACKUP_DIRECTORY);
        File[] backups = backupDir.listFiles((dir, name) -> name.endsWith(".backup"));

        if (backups != null && backups.length > 0) {
            // Sort by last modified time, most recent first
            java.util.Arrays.sort(backups, (f1, f2) ->
                    Long.compare(f2.lastModified(), f1.lastModified()));

            try {
                // Try to load the most recent backup
                BufferedReader reader = new BufferedReader(new FileReader(backups[0]));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                reader.close();

                database = new JSONDeserializer<Database>()
                        .deserialize(json.toString(), Database.class);

                // Save the restored backup as the main database
                saveDatabase();
                return true;

            } catch (IOException e) {
                System.err.println("Error restoring from backup: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void addPlayer(Player player) {
        database.getPlayers().add(player);
        hasUnsavedChanges = true;
        saveDatabase();
    }

    public Player findPlayer(String username) {
        return database.getPlayers().stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public boolean authenticateParental(String password) {
        return database.getParentalPassword().equals(password);
    }

    public void updateParentalPassword(String newPassword) {
        database.setParentalPassword(newPassword);
        hasUnsavedChanges = true;
        saveDatabase();
    }

    public void removePlayer(String username) {
        boolean removed = database.getPlayers().removeIf(p -> p.getUsername().equals(username));
        if (removed) {
            hasUnsavedChanges = true;
            saveDatabase();
        }
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(database.getPlayers());
    }

    public void saveAllChanges() {
        if (hasUnsavedChanges) {
            saveDatabase();
        }
    }

    @Override
    public void close() {
        saveAllChanges();
    }
}
