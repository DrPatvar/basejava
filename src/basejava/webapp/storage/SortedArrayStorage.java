package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(int index, Resume resume) {
        int ind = index * -1 - 1;
        if (size == 0) {
            storage[size] = resume;
            size++;
        } else {
            for (int i = size - 1; i >= 0; i--) {
                storage[i + 1] = storage[i];
            }
            storage[ind] = resume;
            size++;
        }
    }

    @Override
    protected void deleteResume(int index) {
        System.out.println("Элемент " + storage[index] + " удален из массива");
        for (int i = index; i < size; i++) {
            storage[i] = storage[i + 1];
        }
        size--;
    }

    @Override
    protected int findSearchKey(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
