package basejava.webapp.storage;

import basejava.webapp.storage.strategy.JsonStreamSerializer;

public class JsonFileSerializationTest extends AbstractStorageTest {

    public JsonFileSerializationTest() {
        super(new FileStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}
