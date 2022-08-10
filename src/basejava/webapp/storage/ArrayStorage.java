package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public boolean checkResume(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (resume == storage[i]) {
                System.out.println("Резюме " + resume + " найдено в данном массиве.");
                return true;
            }
        }
        return false;
    }

    public boolean checkUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid == storage[i].getUuid())
                System.out.println("указанный элемент: " + uuid + " найден");
            return true;
        }
        return false;
    }

    public boolean checkSize(int size) {
        System.out.println("Проверка места в памяти массива: ");
        if (size <= 9999) {
            return true;
        }
        return false;
    }

    public void clear() {
        System.out.println("Обнуление массива: ");
        Arrays.fill(storage, null);
        size = 0;
    }

    public void update(Resume resume) {
        System.out.println("Обновление резюме..........");
        if (checkResume(resume)) {
            for (int i = 0; i < size; i++) {
                if (storage[i] == resume) {
                    storage[i].setUuid(resume.getUuid());
                }
            }
            System.out.println("Резюме " + resume + " обновлено в данном массиве.");
        }
    }

    public void save(Resume r) {
        System.out.println("Сохранение..........");
        if (checkSize(size)) {
            if (!checkResume(r)) {
                storage[size] = r;
                size++;
                System.out.println("Добавлен элемент " + r + " в массив");
            }
        } else System.out.println("ERROR массив переполнен...");

    }

    public Resume get(String uuid) {
        System.out.println("Поиск и выдача элемента: " + uuid + " в маccиве");
        if (checkUuid(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid() == (uuid))
                    return storage[i];
            }
        } else System.out.println("Элемент: " + uuid + " в массиве не найден");
        return null;
    }

    public void delete(String uuid) {
        System.out.println("Удаление элемента " + uuid + " из массива");
        if (checkUuid(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid() == uuid) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                }
            }
            System.out.println("указанный элемент: " + uuid + " удален из массива");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        System.out.println("Все элементы массива: ");
        Resume[] resumes = new Resume[size];
        return resumes = Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        System.out.println("Количество элементов в массиве: ");
        return size;
    }
}
