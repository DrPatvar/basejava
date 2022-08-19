package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void clear() {
        System.out.println("Обнуление массива: ");
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume r) {
        System.out.println("Обновление резюме...");
        int index = findIndex(r.getUuid());
        if (index < 0) {
            System.out.println("Резюме " + r.getUuid() + " не найдено в массиве.");
        } else {
            storage[index] = r;
            System.out.println("Резюме " + r + " обновлено в данном массиве.");
        }
    }

    @Override
    public void save(Resume r) {
        System.out.println("Сохранение элемента...");
        int index = findIndex(r.getUuid());
        if (index != -1) {
            System.out.println("Данное резюме " + r.getUuid() + " уже существует");
        } else if (size > STORAGE_LIMIT) {
            System.out.println("Массив переполнен");
        } else {
            storage[size] = r;
            size++;
        }
    }

    @Override
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

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        Arrays.sort(storage, Comparator.nullsLast(Comparator.<Resume>naturalOrder()));
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
