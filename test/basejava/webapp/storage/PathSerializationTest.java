package basejava.webapp.storage;

import basejava.webapp.storage.strategy.ObjectStreamSerialization;

public class PathSerializationTest extends AbstractStorageTest {
    private static PathStorage pathStorage = new PathStorage(STORAGE_DIR, new ObjectStreamSerialization());

    public PathSerializationTest() {
        super(pathStorage);
    }
}
