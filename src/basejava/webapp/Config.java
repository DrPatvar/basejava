package basejava.webapp;

import basejava.webapp.storage.SqlStorage;
import basejava.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("config\\resumes.properties");
    private static final Config INSTANCE = new Config();
    private Storage storage;

    private Properties props = new Properties();
    private File storageDir;


    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password"));


        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid config file" + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

    public Properties getProps() {
        return props;
    }
}

