package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);
    private static final String UUID_NOT_EXIST = "dummy";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    public void assertSize(int x) {
        Assert.assertEquals(x, storage.size());
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
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        assertSize(3);
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void update() {
        Assert.assertSame(RESUME_1, storage.get(RESUME_1.getUuid()));
    }


    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST));
    }

    @Test
    public void save() {
    storage.save(RESUME_4);
    assertGet(RESUME_4);
    assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistStorageException() {
        storage.save(new Resume("uuid1"));
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() {
        for (int i = 3; i < 10000; i++) {
            storage.save(new Resume("uuid" + i + 1));
        }
        storage.save(new Resume("newUuid"));
    }

    @Test
    public void delete() {
        storage.delete("uuid1");
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }
}