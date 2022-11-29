package basejava.webapp.storage;

import basejava.webapp.storage.strategy.DataStreamSerialization;

public class DataPathSerializationTest extends AbstractStorageTest {

    public DataPathSerializationTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerialization()));
    }
}
