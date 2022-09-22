package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected ArrayList<Resume> arrayList = new ArrayList<>();

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (uuid.equals(arrayList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        arrayList.add(resume);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        arrayList.remove((int)searchKey);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return arrayList.get(searchKey);
    }

    @Override
    public List<Resume> doCopyAll() {
        return arrayList;
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        arrayList.set(searchKey, resume);
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
