package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> arrayList = new ArrayList<>();

    @Override
    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (uuid.equals(arrayList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        arrayList.add(resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        arrayList.remove((int) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    public Resume doGet(Object searchKey) {
        return arrayList.get((Integer) searchKey);
    }

    @Override
    public List<Resume> doCopyAll() {
        return arrayList;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        arrayList.set((Integer) searchKey, resume);
    }

    @Override
    public int sizeStorage() {
        return arrayList.size();
    }

    @Override
    public void doClear() {
        arrayList.clear();
    }
}
