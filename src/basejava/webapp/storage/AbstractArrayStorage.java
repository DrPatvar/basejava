package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new ExistStorageException(resume.getUuid());
        }
        insertResume(searchKey, resume);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        deleteResume(searchKey);
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage[searchKey];
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
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        for (int i = 0; i <size ; i++) {
            list.add(storage[i]);
        }
        return list;
    }

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}


