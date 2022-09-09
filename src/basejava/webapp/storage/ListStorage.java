package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    ArrayList<Resume> arrayList;

    public ListStorage(Storage storage) {
        this.arrayList = (ArrayList<Resume>) storage;
    }


}
