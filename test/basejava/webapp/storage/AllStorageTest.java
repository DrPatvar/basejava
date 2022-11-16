package basejava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ListStorageTest.class,
        MapUuidStorageTest.class,
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        MapResumeStorageTest.class,
        ObjectFileSerializationTest.class,
        ObjectPathSerializationTest.class,
        XmlFileSerializationTest.class
        //JsonPathSerializationTest.class
        //DataPathSerializationTest.class
})
public class AllStorageTest {
}
