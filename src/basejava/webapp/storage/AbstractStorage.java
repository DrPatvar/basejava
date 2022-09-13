package basejava.webapp.storage;

import basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected Storage storage;

    public AbstractStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void clear() {
        arrayFill();
    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public void save(Resume r) {
        saveStorage(r);
    }



    @Override
    public Resume get(String uuid) {
        return getStorage(uuid);
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return getStorageAll();
    }

    @Override
    public int size() {
        return sizeStorage();
    }

    protected abstract void saveStorage(Resume resume);

    public abstract int sizeStorage();

    public abstract void arrayFill();

    public abstract Resume getStorage(String uuid);
    public abstract Resume[] getStorageAll();
}
