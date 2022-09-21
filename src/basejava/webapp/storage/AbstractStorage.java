package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractStorage implements Storage {


    @Override
    public void clear() {
        doClear();
    }

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistSearchKey(r.getUuid());
        doSave(searchKey, r);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getExistSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    protected Object getExistSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected Object getNotExistSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
       Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
        return doCopyAll().stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return sizeStorage();
    }


    public abstract int sizeStorage();

    public abstract void doClear();

    public abstract Resume doGet(Object searchKey);

    public abstract List<Resume> doCopyAll();

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract Object findSearchKey(String uuid);


}
