package managers;

import data.Database;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import misc.Item;
import misc.Pet;
import misc.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Classes responsible for managing the database and performing any operations on it.
 * uses FlexJSON to edit the JSON database({@link Database}) in the backend.
 *
 * @author Luca Duarte
 * @see Database
 */
public class DatabaseManager implements AutoCloseable {
    /** filepaths to the database itself and the backup saves */
    private static final String DATABASE_FILE = "C:/university_projects/CS2212/group50/src/main/java/data/db.json";
    private static final String BACKUP_DIRECTORY = "C:/university_projects/CS2212/group50/src/main/java/data/backup";
    private static final int MAX_BACKUP_FILES = 5;

    private static DatabaseManager instance;
    private Database database;
    private boolean hasUnsavedChanges;

    /**
     * Default constructor, initalizes the backup directory and loads the database on startup
     */
    public DatabaseManager() {
        hasUnsavedChanges = false;
        initializeBackupDirectory();
        loadDatabase();
    }

    /**
     * Returns instance of DatabeManager
     * @return DatabaseManager instance
     */
    public static DatabaseManager getInstance() {
        if (instance == null)
        {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Initializes the directory which will contain the backups for the database
     */
    private void initializeBackupDirectory() {
        File backupDirectory = new File(BACKUP_DIRECTORY);
        if (!backupDirectory.exists()) {
            backupDirectory.mkdirs();
        }
    }

    /**
     * Loads the JSON into the database class.
     * Uses flexJSON to deserialize the JSON saved in the backend and the places the content within the
     * Database class.
     */
    private void loadDatabase() {
        try {
            /** Checks if JSON database exists, if not creates one */
            File file = new File(DATABASE_FILE);
            if (!file.exists()) {
                database = new Database();
                saveDatabase();
                return;
            }

            /** Uses buffered reader to read through JSON file */
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            /** JSON deserializer which translates all the values in the JSON to the database */
            JSONDeserializer<Database> deserializer = new JSONDeserializer<Database>()
                    .use(null, Database.class)
                    .use("players", ArrayList.class)
                    .use("players.pet.*", Pet.class)
                    .use("players.petList", ArrayList.class)
                    .use("players.petList.type", String.class)
                    .use("players.inventory", HashMap.class)
                    .use("players.inventory.*", Item.class)
                    .use("players.login", Integer.class);

            /** Creates database object from deserialized JSON */
            database = deserializer.deserialize(json.toString());

            /** Remove any empty players after deserialization */
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

    /**
     * Saves data and updates the JSON database.
     * Uses FlexJSON to serialize all the data from the Database class into
     * the JSON file.
     */
    public void saveDatabase() {
        try {
            /** Ensure valid players are in the list (filtering out invalid players) */
            List<Player> validPlayers = database.getPlayers().stream()
                    .filter(player -> player.getUsername() != null && !player.getUsername().isEmpty())
                    .collect(Collectors.toList());

            /** Set the valid players list back to the database */
            database.setPlayers(validPlayers);

            createTimeStampedBackup();

            /** Serialize the entire database to JSON (not just players) */
            JSONSerializer serializer = new JSONSerializer().prettyPrint(true)
                    .include("players")
                    .include("players.login")
                    .include("players.totalPlaytime")
                    .include("players.inventory")
                    .include("players.inventory.*")
                    .include("players.petList")
                    .include("players.pet")
                    .include("players.pet.images")
                    .include("players.pet.type")
                    .include("players.petList.*")
                    .include("players.passwordString")
                    .exclude("*.class");

            String json = serializer.serialize(database);

            /** Write to a temporary file first */
            File tempFile = new File(DATABASE_FILE + "_tmp");
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(json);
            }

            /** If main database file exists, delete it and rename the temporary file */
            File mainFile = new File(DATABASE_FILE);
            if (mainFile.exists()) {
                mainFile.delete();
            }
            tempFile.renameTo(mainFile);

            hasUnsavedChanges = false;
            cleanupOldBackups();
        } catch (IOException e) {
            System.err.println("Error saving database: " + e.getMessage());
            restoreFromBackup();
        }
    }

    /**
     * Creates timestamped backup of the database
     */
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

    /**
     * Cleans old backups if the backup limit exceeds
     */
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

    /**
     * Restored file from backup if database is corrupted
     * @return boolean value
     */
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

    /**
     * Adds new player to the database
     * @param player
     */
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

    /**
     * Finds and returns a player with the given username from the database
     * @param username
     * @return player
     */
    public Player findPlayer(String username) {
        return database.getPlayers().stream()
                .filter(p -> p.getUsername().trim().equalsIgnoreCase(username.trim()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Parental password getter
     * @return
     */
    public String getParentalPassword() {
        return database.getParentalPassword();
    }

    /**
     * Checks if given password matches with given password
     * @param password
     * @return boolean
     */
    public boolean authenticateParental(String password) {
        return database.getParentalPassword().equals(password);
    }

    /**
     * Updates parental password
     * @param newPassword
     * @deprecated
     */
    public void updateParentalPassword(String newPassword) {
        database.setParentalPassword(newPassword);
        hasUnsavedChanges = true;
        saveDatabase();
    }

    /**
     * Removes player from the database
     * @param username
     * @deprecated
     */
    public void removePlayer(String username) {
        boolean removed = database.getPlayers().removeIf(p -> p.getUsername().equals(username));
        if (removed) {
            hasUnsavedChanges = true;
            saveDatabase();
        }
    }

    /**
     * Returns the entire list of players from the database
     * @return
     */
    public List<Player> getAllPlayers() {
        return new ArrayList<>(database.getPlayers());
    }

    /**
     * Saves all changes of the database if there are any unsaved changes
     */
    public void saveAllChanges() {
        if (hasUnsavedChanges) {
            saveDatabase();
        }
    }

    /**
     * Saves the database on close
     */
    @Override
    public void close() {
        saveAllChanges();
    }
}