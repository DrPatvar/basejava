package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest() {
      this.storage = new ArrayStorage();
    }

    @Before
    public void setUp() throws Exception{
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3,storage.size());
    }

    @Test
    public void get() {
    }
    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update(){

    }
    @Test(expected = NotExistStorageException.class)
    public void updateNotExistStorageException() {

    }


    @Test(expected = ExistStorageException.class)
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void clear() {
    }
}