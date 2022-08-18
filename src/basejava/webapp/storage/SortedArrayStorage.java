package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    Resume sortArray;
    @Override
    public void clear() {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public void save(Resume r) {
        System.out.println("Сохранение элемента...");
    int index = findIndex(r.getUuid());
    if (index == size){
        System.out.println("Данное резюме " + r.getUuid() + " уже существует");
    }else if (size>STORAGE_LIMIT ){
        System.out.println("Массив переполнен");
    }else {
     storage[size] = r;
     size++;
    }
    }

    @Override
    public void delete(String uuid) {
    int index = findIndex(uuid);
    if (index>=size){
        System.out.println(uuid + "not exist");
    }else{

    }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage,0,size);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        Arrays.sort(storage, Comparator.nullsLast(Comparator.<Resume>naturalOrder() ));
        return Arrays.binarySearch(storage,0,size,searchKey);
    }
}
