package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int searchKey = getSearchKeyIfExist(resume.getUuid());
        makeSave(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        int searchKey = getSearchKeyIfNotExist(resume.getUuid());
        makeUpdate(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        int searchKey = getSearchKeyIfNotExist(uuid);
        makeDelete(uuid, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        int searchKey = getSearchKeyIfNotExist(uuid);
        return makeGet(uuid, searchKey);
    }

    protected int getSearchKeyIfExist(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey >= 0) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected int getSearchKeyIfNotExist(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract int getSearchKey(String uuid);

    protected abstract void makeSave(Resume resume, int index);

    protected abstract void makeUpdate(Resume resume, int index);

    protected abstract void makeDelete(String uuid, int index);

    protected abstract Resume makeGet(String uuid, int index);
}
