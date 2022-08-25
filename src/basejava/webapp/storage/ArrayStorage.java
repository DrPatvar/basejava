package basejava.webapp.storage;

import basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.out.println("Элемент " + storage[index] + " удален из массива");
        storage[index] = storage[size - 1];
    }

    public int findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                System.out.println("указанный элемент: " + uuid + " найден");
                return i;
            }
        }
        return -1;
    }
}
