package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void clear() {
        arrayClear();
    }

    @Override
    public void save(Resume r) {
        int index = findSearchKey(r.getUuid());
        checkSize(r);
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertResume(index, r);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getStorage(index);
    }

    @Override
    public void delete(String uuid) {
        int index = findSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
        }
    }

    @Override
    public void update(Resume r) {
        int index = findSearchKey(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            updateStorage(index, r);
        }
    }

    @Override
    public Resume[] getAll() {
        return getStorageAll();
    }

    @Override
    public int size() {
        return sizeStorage();
    }


    public abstract int sizeStorage();

    public abstract void arrayClear();

    public abstract Resume getStorage(int index);

    public abstract Resume[] getStorageAll();

    protected abstract void updateStorage(int index, Resume resume);

    protected abstract void checkSize(Resume resume);

    protected abstract int findSearchKey(String uuid);

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
