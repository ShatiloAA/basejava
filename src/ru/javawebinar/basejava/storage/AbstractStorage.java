package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKeyIfNotExist(resume.getUuid());
        makeSave(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKeyIfExist(resume.getUuid());
        makeUpdate(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKeyIfExist(uuid);
        makeDelete(uuid, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKeyIfExist(uuid);
        return makeGet(uuid, searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> unsortedList = listOfResumes();
        return unsortedList.stream().sorted(Comparator.comparing(Resume::getFullname).thenComparing(Resume::getUuid)).collect(Collectors.toList());
    }

    protected Object getSearchKeyIfExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);

        }
        return searchKey;
    }

    protected Object getSearchKeyIfNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract List<Resume> listOfResumes();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void makeSave(Resume resume, Object index);

    protected abstract void makeUpdate(Resume resume, Object index);

    protected abstract void makeDelete(String uuid, Object index);

    protected abstract Resume makeGet(String uuid, Object index);
}
