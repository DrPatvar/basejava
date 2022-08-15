package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

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

    public void update(Resume resume) {
        System.out.println("Обновление резюме..........");
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index].setUuid(resume.getUuid());
            System.out.println("Резюме " + resume + " обновлено в данном массиве.");
        } else System.out.println("Не найдено резюме в массиве.");
    }

    public void save(Resume r) {
        System.out.println("Сохранение..........");
        if (size > STORAGE_LIMIT & size < 0) {
            System.out.println("ERROR");
        } else if (r == null) {
            System.out.println("ERROR");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        System.out.println("Поиск и выдача элемента: " + uuid + " в маccиве");
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Элемент: " + uuid + " в массиве не найден");
        }
        return null;
    }

    public void delete(String uuid) {
        System.out.println("Удаление элемента " + uuid + " из массива");
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.println("указанный элемент: " + uuid + " удален из массива");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        System.out.println("Все элементы массива: ");
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        System.out.println("Количество элементов в массиве: ");
        return size;
    }
}
