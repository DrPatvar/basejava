package basejava.webapp.storage;

import basejava.webapp.storage.strategy.JsonStreamSerializer;

public class JsonPathSerializationTest extends AbstractStorageTest {

    public JsonPathSerializationTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
}
