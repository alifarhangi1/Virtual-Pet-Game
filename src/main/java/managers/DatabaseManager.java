//package managers;
//
//import data.Database;
//import flexjson.JSONDeserializer;
//import flexjson.JSONSerializer;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//import java.io.*;
//
//public class DatabaseManager implements AutoCloseable {
//    private static final String DATABASE_FILE = "db.json";
//    private static final String BACKUP_FILE = "backup.json";
//    private static final String BACKUP_DIRECTORY = "src/backups/";
//    private Database database;
//    private boolean hasUnsavedChanges;
//
//    public DatabaseManager() {
//        hasUnsavedChanges = false;
//        initializeBackupDirectory();
//        loadDatabase();
//    }
//
//    private void initializeBackupDirectory() {
//        File backupDirectory = new File(BACKUP_DIRECTORY);
//        if (!backupDirectory.exists()) {
//            backupDirectory.mkdirs();
//        }
//    }
//
//    private void loadDatabase() {
//        try {
//            File file = new File(DATABASE_FILE);
//            if (!file.exists()) {
//                database = new Database();
//                saveDatabase();
//                return;
//            }
//
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            StringBuilder json = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                json.append(line);
//            }
//            reader.close();
//
//            database = new JSONDeserializer<Database>().deserialize(json.toString(), Database.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void close() throws Exception {
//
//    }
//}
