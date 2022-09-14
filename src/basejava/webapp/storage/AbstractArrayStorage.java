package basejava.webapp.storage;

import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public Resume getStorage(int index) {
        return storage[index];
    }

    @Override
    public int sizeStorage() {
        return size;
    }

    @Override
    public void arrayClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void checkSize(Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected void updateStorage(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    public Resume[] getStorageAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

}


