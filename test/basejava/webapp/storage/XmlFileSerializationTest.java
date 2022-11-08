package basejava.webapp.storage;

import basejava.webapp.storage.strategy.XmlStreamSerializer;

public class XmlFileSerializationTest extends AbstractStorageTest {

    public XmlFileSerializationTest() {
        super(new FileStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}
