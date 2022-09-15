package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public void clear() {
        doClear();
    }

    @Override
    public void save(Resume r) {
        int searchKey = getNotExistSearchKey(r.getUuid());
        isOverFlow(r);
        doSave(searchKey, r);
    }

    @Override
    public Resume get(String uuid) {
        int searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        int searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public void update(Resume r) {
        int index = findSearchKey(r.getUuid());
        if (!isExist(index)) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            doUpdate(index, r);
        }
    }

    protected int getExistSearchKey(String uuid) {
        int index = findSearchKey(uuid);
        if (isExist(index)) {
            return index;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected int getNotExistSearchKey(String uuid) {
        int index = findSearchKey(uuid);
        if (!isExist(index)) {
            return index;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return doCopyAll();
    }

    @Override
    public int size() {
        return sizeStorage();
    }


    public abstract int sizeStorage();

    public abstract void doClear();

    public abstract Resume doGet(int index);

    public abstract Resume[] doCopyAll();

    protected abstract void isOverFlow(Resume resume);

    protected abstract void doSave(int index, Resume resume);

    protected abstract void doDelete(int index);

    protected abstract boolean isExist(int index);

    protected abstract void doUpdate(int index, Resume resume);

    protected abstract int findSearchKey(String uuid);


}
