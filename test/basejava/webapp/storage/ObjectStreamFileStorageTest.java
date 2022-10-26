package basejava.webapp.storage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamFIleStorage(STORAGE_DIR));
    }
}
