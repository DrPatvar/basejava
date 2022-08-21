package basejava.webapp.storage;

import basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        Arrays.sort(storage, Comparator.nullsLast(Comparator.naturalOrder()));
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
