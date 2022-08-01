/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        System.out.println("Обнуление массива: ");
        for (int i = 0; i < size - 1; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        System.out.println("Поиск и выдача элемента: " + uuid + " в маccиве Resume.");
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == (uuid))
                return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        System.out.println("Удаление элемента из массива");
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == (uuid)) {
                for (int j = i; j < size-1; j++) {
                    storage[j] = storage[j + 1];
                }
            }
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        System.out.println("Количество элементов в массиве: ");
        return size;
    }
}
