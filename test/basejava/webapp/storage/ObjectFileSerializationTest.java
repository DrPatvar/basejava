package basejava.webapp.storage;

import basejava.webapp.storage.strategy.ObjectStreamSerialization;

public class ObjectFileSerializationTest extends AbstractStorageTest {

    public ObjectFileSerializationTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerialization()));
    }
}
