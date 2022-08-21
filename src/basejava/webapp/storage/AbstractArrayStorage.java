package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        System.out.println("Поиск и выдача элемента: " + uuid + " в маccиве");
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Элемент: " + uuid + " в массиве не найден");
            return null;
        }
        return storage[index];
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
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
        System.out.println("Удаление элемента из массива...");
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме " + uuid + " не найдено в массиве.");
        } else {
            for (int i = index; i < size; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
        }
    }

    public void clear() {
        System.out.println("Обнуление массива: ");
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected abstract int findIndex(String uuid);
}
