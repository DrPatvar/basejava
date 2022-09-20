package basejava.webapp.storage;

import basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    public void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getFullName()));
    }

    @Override
    @Test
    public void updateMap() {
    }
}
