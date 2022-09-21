package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapResumeStorage extends AbstractStorage {
    protected HashMap<Object, Resume> hashMap = new HashMap<Object, Resume>();


    @Override
    protected Object findSearchKey(String uuid) {
       return hashMap.get(uuid);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        hashMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        hashMap.remove(((Resume)searchKey).getUuid());
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey !=null;
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        hashMap.put(resume.getUuid(), (Resume) searchKey);
    }

    @Override
    public void doClear() {
        hashMap.clear();
    }

    @Override
    public Resume doGet(Object searchKey) {
        return (Resume) searchKey;
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
