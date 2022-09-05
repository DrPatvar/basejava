package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Resume resume = new Resume("uuid1");
        Assert.assertEquals(resume, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume resume1 = new Resume("uuid1");
        Resume resume2 = new Resume("uuid2");
        Resume resume3 = new Resume("uuid3");
        List expected = new ArrayList();
        expected.add(resume1);
        expected.add(resume2);
        expected.add(resume3);
        Assert.assertEquals(expected, Arrays.stream(storage.getAll()).toList());
    }

    @Test
    public void update() {
        storage.update(new Resume("uuid1"));

    }


    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageException() {
        storage.update(new Resume("newUuid"));
    }

    @Test
    public void save() {
        Resume resume = new Resume("newUuid");
        Resume savedProduct = storage.save(resume);
        Assert.assertEquals(resume, savedProduct);
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