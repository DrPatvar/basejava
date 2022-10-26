package basejava.webapp.storage;

import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;

import java.io.*;

public class ObjectStreamFIleStorage extends AbstractFileStorage {


    public ObjectStreamFIleStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(os)){
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try(ObjectInputStream ois = new ObjectInputStream(is)){
            return  (Resume) ois.readObject();
        }catch (ClassNotFoundException e){
            throw  new StorageException("Error read resume", null, e);
        }
    }
}
