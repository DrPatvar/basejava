package basejava.webapp.storage;

import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected File[] getAllFiles() {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("IO error", "что то погло не так");
        }
        return listFiles;
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();

        } catch (IOException e) {
            throw new StorageException("Couldn`t creat file" + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r,file);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        file.delete();
        if (isExist(file)) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    public Resume doGet(File file) {
        Resume resume = null;
        try {
            resume = doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
        return resume;
    }


    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void doClear() {
        for (File file : getAllFiles()) {
            doDelete(file);
        }
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> resumeList = new ArrayList<>();
        for (File file : getAllFiles()) {
            resumeList.add(doGet(file));
        }
        return resumeList;
    }

    @Override
    public int sizeStorage() {
        return getAllFiles().length;
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }
}
