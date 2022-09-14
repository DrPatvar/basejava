package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> arrayList = new ArrayList();

    @Override
    protected int findSearchKey(String uuid) {
        return arrayList.indexOf(uuid);
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        arrayList.add(resume);
    }

    @Override
    protected void deleteResume(int index) {
        arrayList.remove(index);
    }

    @Override
    protected void checkSize(Resume resume) {
    }

    @Override
    public Resume getStorage(int index) {
        return arrayList.get(index);
    }

    @Override
    public Resume[] getStorageAll() {
        return arrayList.toArray(new Resume[arrayList.size()]);
    }

    @Override
    protected void updateStorage(int index, Resume resume) {
        arrayList.set(index, resume);
    }

    @Override
    public int sizeStorage() {
        return arrayList.size();
    }

    @Override
    public void arrayClear() {
        arrayList.clear();
    }

}
