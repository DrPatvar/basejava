package basejava.webapp.storage;

import basejava.webapp.storage.strategy.ObjectStreamSerialization;

public class FileSerializationTest extends AbstractStorageTest {
  private static FileStorage fileStorage = new FileStorage(STORAGE_DIR, new ObjectStreamSerialization());

    public FileSerializationTest(){
        super(fileStorage);
    }
}
