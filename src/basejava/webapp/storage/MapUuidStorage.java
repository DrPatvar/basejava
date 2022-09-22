package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapUuidStorage extends AbstractStorage<String> {
    protected HashMap<String, Resume> hashMap = new HashMap<>();

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Resume resume, String uuid) {
        hashMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(String uuid) {
        hashMap.remove(uuid);
    }

    @Override
    protected boolean isExist(String  uuid) {
        return hashMap.containsKey(uuid);
    }

    @Override
    protected void doUpdate(Resume resume, String uuid) {
        hashMap.put(uuid, resume);
    }

    @Override
    public void doClear() {
        hashMap.clear();
    }

    @Override
    public Resume doGet(String uuid) {
        return hashMap.get(uuid);
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(hashMap.values());
    }

    @Override
    public int sizeStorage() {
        return hashMap.size();
    }
}
