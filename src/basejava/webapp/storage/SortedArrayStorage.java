package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    public SortedArrayStorage() {
        super(new SortedArrayStorage());
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);//size - insertionIndex
        storage[insertionIndex] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }

    @Override
    protected int findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
