package basejava.webapp;

import basejava.webapp.model.Resume;
import basejava.webapp.storage.AbstractArrayStorage;
import basejava.webapp.storage.ArrayStorage;
import basejava.webapp.storage.SortedArrayStorage;
import basejava.webapp.storage.Storage;

/**
 * Test for your basejava.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid3");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid1");
        Resume r4 = new Resume();
        r4.setUuid("uuid4");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.update(r2);
        printAll();
        ARRAY_STORAGE.delete(r2.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
