package basejava.webapp.storage;

import basejava.webapp.strategy.ObjectStreamSerialization;

import java.io.IOException;

public class FileSerializationTest extends AbstractStorageTest {
  private static FileStorage fileStorage = new FileStorage(STORAGE_DIR);
    static {
        fileStorage.setSaveStrategy(new ObjectStreamSerialization());
    }
    public FileSerializationTest(){
        super(fileStorage);
    }
}
