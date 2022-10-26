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
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    public AbstractPathStorage(String dir) {
    directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected Stream<Path> getAllFiles() {
        try {
             return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("IO error", "что то погло не так");
        }
    }

    @Override
    protected void doSave(Resume r, Path Path) {
        try {
          Files.createFile(Path);
        } catch (IOException e) {
            throw new StorageException("Couldn't`t creat path" + Path.getFileName(), Path.toString(), e);
        }
        doUpdate(r, Path);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
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
            resume = doRead(new BufferedInputStream(Files.newInputStream(path)));
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
        getAllFiles().forEach(this::doDelete);
    }

    @Override
    public List<Resume> doCopyAll() {
        return getAllFiles().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public int sizeStorage() {
        return (int) getAllFiles().count();
    }

    @Override
    protected Path findSearchKey(String uuid) {
      return directory.resolve(uuid);
    }
}
