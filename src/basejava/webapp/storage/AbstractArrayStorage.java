package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public AbstractArrayStorage(Storage storage) {
        super(storage);
    }
/*public Resume get(String uuid) {
         int index = findSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }*/

    @Override
    public int sizeStorage() {
        return size;
    }

    @Override
    public void arrayFill() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = findSearchKey(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    public void saveStorage (Resume r) {
        int index = findSearchKey(r.getUuid());
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertResume(index, r);
            size++;
        }
    }

    public void delete(String uuid) {
        int index = findSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
            size--;
        }
    }

    @Override
    public Resume[] getStorageAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }
    /* public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }*/

    protected abstract int findSearchKey(String uuid);

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
