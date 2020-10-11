package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract List<Resume> listOfResumes();

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void makeSave(Resume resume, SK searchKey);

    protected abstract void makeUpdate(Resume resume, SK searchKey);

    protected abstract void makeDelete(SK searchKey);

    protected abstract Resume makeGet(SK searchKey);

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getSearchKeyIfNotExist(resume.getUuid());
        makeSave(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getSearchKeyIfExist(resume.getUuid());
        makeUpdate(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getSearchKeyIfExist(uuid);
        makeDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getSearchKeyIfExist(uuid);
        return makeGet(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> unsortedList = listOfResumes();
        return unsortedList.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Resume::getFullName)
                .thenComparing(Resume::getUuid))
                .collect(Collectors.toList());
    }

    private SK getSearchKeyIfExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist!");
            throw new NotExistStorageException(uuid);

        }
        return searchKey;
    }

    private SK getSearchKeyIfNotExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist!");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}
