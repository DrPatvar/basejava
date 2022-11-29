package basejava.webapp.storage;

import basejava.webapp.storage.strategy.ObjectStreamSerialization;

public class ObjectPathSerializationTest extends AbstractStorageTest {

    public ObjectPathSerializationTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerialization()));
    }
}
