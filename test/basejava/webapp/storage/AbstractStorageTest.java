package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AbstractStorageTest {

    protected Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME1 = "Resume";
    private static final String FULLNAME2 = "Resume";
    private static final String FULLNAME3 = "Resume";
    private static final String FULLNAME4 = "Resume";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;
    private static final String UUID_NOT_EXIST = "dummy";

    static {
        RESUME_1 = new Resume(UUID_1, FULLNAME1);
        RESUME_2 = new Resume(UUID_2, FULLNAME2);
        RESUME_3 = new Resume(UUID_3, FULLNAME3);
        RESUME_4 = new Resume(UUID_4, FULLNAME4);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    public void assertSize(int expectedSize) {
        Assert.assertEquals(expectedSize, storage.size());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    public void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void getAll() {
        List<Resume> expected =  new ArrayList<>();
        expected.add(RESUME_1);
        expected.add(RESUME_2);
        expected.add(RESUME_3);
        assertSize(3);

        Assert.assertArrayEquals(expected.toArray(), storage.getAllSorted().toArray());
    }

    @Test
    public void update() {
        Assert.assertSame(RESUME_1, storage.get(RESUME_1.getUuid()));
    }


    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST, ""));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1, FULLNAME1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        assertGet(RESUME_1);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> resumes = new ArrayList<>();
        Assert.assertArrayEquals(resumes.toArray(),
                storage.getAllSorted().toArray());
    }
}