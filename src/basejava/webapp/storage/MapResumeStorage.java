package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected HashMap<Object, Resume> hashMap = new HashMap<Object, Resume>();


    @Override
    protected Resume findSearchKey(String uuid) {
       return hashMap.get(uuid);
    }

    @Override
    protected void doSave(Resume r, Resume resume) {
        hashMap.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Resume resume) {
        hashMap.remove(resume.getUuid());
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume !=null;
    }

    @Override
    protected void doUpdate(Resume r, Resume resume) {
        hashMap.put(r.getUuid(), resume);
    }

    @Override
    public void doClear() {
        hashMap.clear();
    }

    @Override
    public Resume doGet(Resume resume) {
        return resume;
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
