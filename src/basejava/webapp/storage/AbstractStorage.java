package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;

import static basejava.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractStorage implements Storage {

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
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
        return size;
    }

    protected abstract int findSearchKey(String uuid);

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
