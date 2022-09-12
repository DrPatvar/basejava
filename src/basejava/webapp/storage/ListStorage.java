package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    @Override
    protected int findSearchKey(String uuid) {
        if (uuid == null) {
            for (int i = 0; i < size; i++) {
                if (storage[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        final int s;
        if ((s = size) == storage.length)
        System.arraycopy(storage, index,
                storage, index + 1,
                s - index);
        storage[index] = resume;
        size = s + 1;

    }

    @Override
    protected void deleteResume(int index) {
        final int newSize;
        if ((newSize = size - 1) > index)
            System.arraycopy(storage, index + 1, storage, index, newSize - index);
        storage[size = newSize] = null;
    }
}
