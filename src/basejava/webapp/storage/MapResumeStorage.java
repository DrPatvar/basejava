package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapResumeStorage extends AbstractStorage {
    protected HashMap<Object, Resume> hashMap = new HashMap<Object, Resume>();


    @Override
    protected Resume findSearchKey(String uuid) {
       return hashMap.get(uuid);
    }

    @Override
    protected void doSave(Resume r, Object resume) {
        hashMap.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Object resume) {
        hashMap.remove(((Resume)resume).getUuid());
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume !=null;
    }

    @Override
    protected void doUpdate(Resume r, Object resume) {
        hashMap.put(r.getUuid(), (Resume) resume);
    }

    @Override
    public void doClear() {
        hashMap.clear();
    }

    @Override
    public Resume doGet(Object resume) {
        return (Resume) resume;
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
