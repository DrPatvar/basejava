package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected boolean isExist(int index) {
        if (index >= 0){
            return true;
        }
        else return false;
    }

    @Override
    protected void doSave(int index, Resume resume) {
        insertResume(index, resume);
    }

    @Override
    protected void doDelete(int index) {
        deleteResume(index);
    }

    @Override
    protected int findSearchKey(String uuid) {
        return 0;
    }

    @Override
    public Resume doGet(int index) {
        return storage[index];
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
    protected void doUpdate(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    public Resume[] doCopyAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}


