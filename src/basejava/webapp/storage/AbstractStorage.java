package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractStorage implements Storage {

    protected Resume[] storage;
    protected int size = 0;

    @Override
    public void clear() {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public Resume save(Resume r) {
        return null;
    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
    protected abstract int findSearchKey(String uuid);

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
