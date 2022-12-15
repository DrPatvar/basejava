package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private static final Comparator COMPARATOR = Comparator.comparing(Resume::getUuid).thenComparing(Resume::getFullName);

    protected abstract SK findSearchKey(String uuid);

    public abstract int sizeStorage();

    public abstract void doClear();

    public abstract Resume doGet(SK searchKey);

    public abstract List<Resume> doCopyAll();

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doUpdate(Resume resume, SK searchKey);


    @Override
    public void clear() {
        LOG.info("CLEAR");
        doClear();
    }

    @Override
    public void save(Resume r) {
        LOG.info("SAVE " + r) ;
        SK searchKey = getNotExistSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("GET "+ uuid);
        SK searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("DELETE " + uuid);
        SK searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public void update(Resume r) {
        LOG.info("UPDATE " + r) ;
        SK searchKey = getExistSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    protected SK getExistSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            LOG.warning("Resume " + uuid +  " not exist");
            throw new NotExistStorageException(uuid);
        }
    }

    protected SK getNotExistSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            return searchKey;
        } else {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> resumes = doCopyAll();
        resumes.sort(COMPARATOR);
        return resumes;
    }

    @Override
    public int size() {
        return sizeStorage();
    }


}
