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
        int index = findSearchKey(uuid);
        if (index < 0) {
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
        int index = findSearchKey(r.getUuid());
        if (index < 0) {
            System.out.println("Не найдено резюме в массиве.");
        } else {
            storage[index] = r;
            System.out.println("Резюме " + r + " обновлено в данном массиве.");
        }
    }

    public void save(Resume r) {
        System.out.println("Сохранение..........");
        int index = findSearchKey(r.getUuid());
        if (size > STORAGE_LIMIT) {
            System.out.println("Массив переполнен.");
        } else if (index > 0) {
            System.out.println("Данное резюме " + r.getUuid() + " уже существует");
        } else {
            insertResume(index, r);
            size++;
        }
    }

    public void delete(String uuid) {
        System.out.println("Удаление элемента из массива...");
        int index = findSearchKey(uuid);
        if (index < 0) {
            System.out.println("Резюме " + uuid + " не найдено в массиве.");
        } else {
            deleteResume(index);
            size--;
        }
    }

    public void clear() {
        System.out.println("Обнуление массива: ");
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected abstract int findSearchKey(String uuid);

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

}
