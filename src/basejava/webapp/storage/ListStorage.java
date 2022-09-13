package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;

public class ListStorage extends AbstractStorage {
    private final ArrayList arrayList = new ArrayList();

    public ListStorage() {
        super((Storage) new ArrayList());
    }

    @Override
    protected void saveStorage(Resume resume) {
        arrayList.add(resume);
    }

    @Override
    public void arrayFill() {
        Collections.emptyList();
    }

    @Override
    public Resume getStorage(String uuid) {
       // return arrayList.get(uuid);
    }

    @Override
    public Resume[] getStorageAll() {
        return null;
    }

    @Override
    public int sizeStorage() {
        return arrayList.size();
    }

}
