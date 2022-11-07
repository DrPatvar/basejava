package basejava.webapp.storage;

import basejava.webapp.strategy.ObjectStreamSerialization;

public class PathSerializationTest extends AbstractStorageTest {
  private static PathStorage pathStorage = new PathStorage(STORAGE_DIR);
    static {
        pathStorage.setSaveStrategy(new ObjectStreamSerialization());
    }
    public PathSerializationTest(){
        super(pathStorage);
    }
}
