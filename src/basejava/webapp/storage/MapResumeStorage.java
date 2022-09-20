package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.List;

public class MapResumeStorage extends AbstractStorage {
    protected LinkedHashMap<Object, Resume> hashMap = new LinkedHashMap<>();

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistSearchKey(r.getFullName());
        doSave(searchKey, r);
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getExistSearchKey(r.getFullName());
        doUpdate(searchKey, r);
    }

    @Override
    protected Object findSearchKey(String fullName) {
        if (hashMap.containsKey(fullName)) {
            return fullName;
        }
        return -1;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        hashMap.put(resume.getFullName(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        hashMap.remove(searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return hashMap.containsKey(searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        hashMap.put(searchKey, resume);
    }

    @Override
    public void doClear() {
        hashMap.clear();
    }

    @Override
    public Resume doGet(Object searchKey) {
        return hashMap.get(searchKey);
    }

    @Override
    public List<Resume> doCopyAll() {
        return hashMap.values().stream().toList();
    }

    @Override
    public int sizeStorage() {
        return hashMap.size();
    }
}
