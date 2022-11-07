package basejava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ListStorageTest.class,
        MapUuidStorageTest.class,
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        MapResumeStorageTest.class,
        FileSerializationTest.class,
        PathSerializationTest.class})
public class AllStorageTest {
}
