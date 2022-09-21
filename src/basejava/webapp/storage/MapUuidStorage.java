package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.List;

public class MapUuidStorage extends AbstractStorage {
    protected HashMap<Object, Resume> hashMap = new HashMap<>();

    @Override
    protected Object findSearchKey(String uuid) {
        if (hashMap.containsKey(uuid)) {
            return uuid;
        }
        return -1;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
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
    protected void doUpdate(Resume resume, Object searchKey) {
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
