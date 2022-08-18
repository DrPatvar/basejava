package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                System.out.println("указанный элемент: " + uuid + " найден");
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        System.out.println("Обнуление массива: ");
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        System.out.println("Обновление резюме..........");
        int index = findIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Не найдено резюме в массиве.");
        } else {
            storage[index] = r;
            System.out.println("Резюме " + r + " обновлено в данном массиве.");
        }
    }

    public void save(Resume r) {
        System.out.println("Сохранение..........");
        int index = findIndex(r.getUuid());
        if (size > STORAGE_LIMIT) {
            System.out.println("Массив переполнен.");
        } else if (index != -1) {
            System.out.println("Данное резюме " + r.getUuid() + " уже существует");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void delete(String uuid) {
        System.out.println("Удаление элемента " + uuid + " из массива");
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Элемент: " + uuid + " в массиве не найден.");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.println("Указанный элемент: " + uuid + " удален из массива");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

}
