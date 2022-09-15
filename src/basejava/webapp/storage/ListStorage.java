package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> arrayList = new ArrayList<>();

    @Override
    protected int findSearchKey(String uuid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (uuid.equals(arrayList.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(int index, Resume resume) {
        arrayList.add(resume);
    }

    @Override
    protected void doDelete(int index) {
        arrayList.remove(index);
    }

    @Override
    protected boolean isExist(int index) {
        return index >= 0;
    }

    @Override
    public Resume doGet(int index) {
        return arrayList.get(index);
    }

    @Override
    public Resume[] doCopyAll() {
        return arrayList.toArray(new Resume[arrayList.size()]);
    }

    @Override
    protected void doUpdate(int index, Resume resume) {
        arrayList.set(index, resume);
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
