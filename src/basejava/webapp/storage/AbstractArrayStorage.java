package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new ExistStorageException(resume.getUuid());
        }
        insertResume((Integer) searchKey, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        deleteResume((Integer) searchKey);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    public int sizeStorage() {
        return size;
    }

    @Override
    public void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    public Resume[] doCopyAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}


