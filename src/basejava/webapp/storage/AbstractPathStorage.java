package basejava.webapp.storage;

import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)){
            throw new IllegalArgumentException(dir + " is not directory or is not writable" );
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected Path[] getAllFiles() {
        Path[] listFiles = new Path[0] ;
        try {
            listFiles = (Path[]) Files.list(directory).toArray();
        } catch (IOException e) {
            throw new StorageException("IO error", "что то погло не так");
        }
        return listFiles;
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn`t creat path" + path.getFileName(), path.toString(), e);
        }
        doUpdate(r,path);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString());
        }
    }

    @Override
    public Resume doGet(Path path) {
        Resume resume = null;
        try {
            resume = doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
        return resume;
    }


    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void doClear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw  new StorageException("Path delete error", null);
        }
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> resumeList = new ArrayList<>();
        for (Path path : getAllFiles()) {
            resumeList.add(doGet(path));
        }
        return resumeList;
    }

    @Override
    public int sizeStorage() {
        return getAllFiles().length;
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return null;
    }
}
