package basejava.webapp.storage;

import basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
   private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        System.out.println("Обнуление массива: ");
        for (int i = 0; i < size - 1; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        System.out.println("Поиск и выдача элемента: " + uuid + " в маccиве basejava.webapp.model.Resume.");
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == (uuid))
                return storage[i];
        }
        return null;
    }

   public void delete(String uuid) {
        System.out.println("Удаление элемента из массива");
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
               storage[i] = storage[size - 1];
               storage[size -1 ] = null;
               size --;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public int size() {
        System.out.println("Количество элементов в массиве: ");
        return size;
    }
}
