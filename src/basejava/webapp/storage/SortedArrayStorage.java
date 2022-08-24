package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(int index, Resume resume) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size);
        storage[insertionIndex] = resume;
        size++;
    }

    @Override
    protected void deleteResume(int index) {
        System.out.println("Элемент " + storage[index] + " удален из массива");
        System.arraycopy(storage, index + 1, storage, index, size);
        size--;
    }

    @Override
    protected int findSearchKey(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
