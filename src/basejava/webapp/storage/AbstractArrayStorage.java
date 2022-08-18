package basejava.webapp.storage;

import basejava.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size(){
        return size;
    }

    public Resume get(String uuid){
        System.out.println("Поиск и выдача элемента: " + uuid + " в маccиве");
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Элемент: " + uuid + " в массиве не найден");
            return null;
        }
        return storage[index];
    }
    protected abstract int findIndex(String uuid);
}
