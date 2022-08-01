/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10];
    int size = 0;

    void clear() {
        System.out.println("Обнуление массива: ");
        for (int i = 0; i < size-1; i++) {
            storage[i] = null;
        }
        size=0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (Resume resum : storage
        ) {
            try {
                if (resum.uuid.equals(uuid)) return resum;
            } catch (NullPointerException e) {
            }
        }
        return null;
    }

    void delete(String uuid) {
        System.out.println("Удаление элемента из массива");
        Resume[] cloneRes = new Resume[storage.length];
        for (int i = 0; i < storage.length; i++) {
            cloneRes[i] = storage[i];
        }
        try {
            for (int i = 0; i < storage.length; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    for (int j = i + 1; j < cloneRes.length; j++) {
                        storage[i] = cloneRes[j];
                    }
                    size--;
                    break;
                }
            }
        } catch (NullPointerException e) {

        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
            Resume[] resumes = new Resume[size];
        for (int i = 0; i < size ; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        System.out.println("Количество элементов в массиве: ");
        return size;
    }
}
