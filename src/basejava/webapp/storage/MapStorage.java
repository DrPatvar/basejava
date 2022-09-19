package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.LinkedHashMap;

public class MapStorage extends AbstractStorage {
    protected LinkedHashMap<Object, Resume> hashMap = new LinkedHashMap<>();

    @Override
    protected Object findSearchKey(String uuid) {
        if (hashMap.containsKey(uuid)) {
            return uuid;
        }
        return -1;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        hashMap.put(resume.getUuid(), resume);
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
    public Resume[] doCopyAll() {
        return hashMap.values().toArray(new Resume[0]);
    }

    @Override
    public int sizeStorage() {
        return hashMap.size();
    }
}
