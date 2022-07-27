import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[5];

    void clear() {
        System.out.println("отчистка массива");
        for (int i = 0; i <storage.length ; i++) {
            if (storage[i]!=null){
                storage[i]=null;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i <storage.length ;i++) {
            if (storage[i]==null) {
                storage[i] = r;
                break;
            }
        }

    }

    Resume get(String uuid) {
        for (Resume resum: storage
             ) {
            try {if (resum.uuid.equals(uuid)) return resum;
            }catch (NullPointerException e){
            }
        }return null;
    }

    void delete(String uuid) {
        System.out.println("Удаление элемента из массива");
        Resume[] cloneRes = new Resume[storage.length];
        for (int i = 0; i <storage.length ; i++) {
            cloneRes[i]=storage[i];
        }
        try{
            for (int i = 0; i <storage.length ; i++) {
                if (storage[i].uuid.equals(uuid)){
                    for (int j = i+1; j <cloneRes.length; j++) {
                        storage[i]=cloneRes[j];
                    }
                    break;
                }
            }
        }catch (NullPointerException e){

        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return storage;
    }

    int size() {
        int count = 0;
        for (int i = 0; i <storage.length ; i++) {
            if (storage[i]!=null)count++;
        }
        return count;
    }
}
