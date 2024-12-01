package managers;

import data.Database;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import misc.Pet;
import misc.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseManager implements AutoCloseable {
    private static final String DATABASE_FILE = "C:/university_projects/CS2212/group50/src/main/java/data/db.json";
    private static final String BACKUP_DIRECTORY = "C:/university_projects/CS2212/group50/src/main/java/data/backup";
    private static final int MAX_BACKUP_FILES = 5;

    private static DatabaseManager instance;
    private Database database;
    private boolean hasUnsavedChanges;

    public DatabaseManager() {
        hasUnsavedChanges = false;
        initializeBackupDirectory();
        loadDatabase();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
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

            // Create a more robust deserializer
            JSONDeserializer<Database> deserializer = new JSONDeserializer<Database>()
                    .use(null, Database.class)
                    .use("players", ArrayList.class)
                    .use("players.elementType", Player.class)
                    .use("players.inventory", HashMap.class)
                    .use("players.petList", Pet[].class)
                    .use("players.miniGame", boolean[].class);

            database = deserializer.deserialize(json.toString());

            // Remove any empty players after deserialization
            database.getPlayers().removeIf(player ->
                    player.getUsername() == null || player.getUsername().isEmpty());
        }
        catch (IOException e) {
            System.err.println("Error loading database: " + e.getMessage());
            if (!restoreFromBackup()) {
                database = new Database();
            }
        }
    }

    private void saveDatabase() {
        try {
            System.out.println("Attempting to save database...");

            // Ensure valid players are in the list (filtering out invalid players)
            List<Player> validPlayers = database.getPlayers().stream()
                    .filter(player -> player.getUsername() != null && !player.getUsername().isEmpty())
                    .collect(Collectors.toList());

            // Set the valid players list back to the database
            database.setPlayers(validPlayers);

            createTimeStampedBackup();

            // Serialize the entire database to JSON (not just players)
            JSONSerializer serializer = new JSONSerializer().prettyPrint(true)
                    .include("players")
                    .include("players.inventory")
                    .include("players.petList")
                    .include("players.miniGame")
                    .include("players.passwordString")
                    .exclude("*.class");

            String json = serializer.serialize(database);
            System.out.println("Serialized JSON: " + json);

            // Write to a temporary file first
            File tempFile = new File(DATABASE_FILE + "_tmp");
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(json);
            }

            // If main database file exists, delete it and rename the temporary file
            File mainFile = new File(DATABASE_FILE);
            if (mainFile.exists()) {
                mainFile.delete();
            }
            tempFile.renameTo(mainFile);

            System.out.println("Database saved successfully to: " + mainFile.getAbsolutePath());

            hasUnsavedChanges = false;
            cleanupOldBackups();
        } catch (IOException e) {
            System.err.println("Error saving database: " + e.getMessage());
            restoreFromBackup();
        }
    }

    private void createTimeStampedBackup() {
        try {
            File currentFile = new File(DATABASE_FILE);
            if (!currentFile.exists()) {
                System.err.println("Database file does not exist.");
                return;
            }

            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File backupFile = new File(BACKUP_DIRECTORY + "/db_" + timestamp + ".backup");

            // Ensure backup directory exists
            backupFile.getParentFile().mkdirs();

            try (FileInputStream fis = new FileInputStream(currentFile);
                 FileOutputStream fos = new FileOutputStream(backupFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }

    private void cleanupOldBackups() {
        File backupDir = new File(BACKUP_DIRECTORY);
        File[] backups = backupDir.listFiles((dir, name) -> name.endsWith(".backup"));

        if (backups != null && backups.length > MAX_BACKUP_FILES) {
            // Sort by last modified time, and delete the oldest backups
            java.util.Arrays.sort(backups, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
            for (int i = MAX_BACKUP_FILES; i < backups.length; i++) {
                backups[i].delete();
            }
        }
    }

    private boolean restoreFromBackup() {
        File backupDir = new File(BACKUP_DIRECTORY);
        File[] backups = backupDir.listFiles((dir, name) -> name.endsWith(".backup"));

        if (backups != null && backups.length > 0) {
            java.util.Arrays.sort(backups, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
            try {
                BufferedReader reader = new BufferedReader(new FileReader(backups[0]));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                reader.close();

                JSONDeserializer<Database> deserializer = new JSONDeserializer<Database>()
                        .use(null, Database.class);

                database = deserializer.deserialize(json.toString());
                saveDatabase();
                return true;
            } catch (IOException e) {
                System.err.println("Error restoring from backup: " + e.getMessage());
            }
        }
        return false;
    }

    public void addPlayer(Player player) {
        if (player != null && player.getUsername() != null && !player.getUsername().isEmpty()) {
            // Add the new player to the list (appending)
            database.getPlayers().add(player);
            hasUnsavedChanges = true;
            saveDatabase();  // Save the updated database after adding the new player
        } else {
            System.err.println("scrap.Player username cannot be null or empty!");
        }
    }

    public Player findPlayer(String username) {
        return database.getPlayers().stream()
                .filter(p -> p.getUsername().trim().equalsIgnoreCase(username.trim()))
                .findFirst()
                .orElse(null);
    }

    public String getParentalPasword() {
        return database.getParentalPassword();
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