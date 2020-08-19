package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        int index = isExist(resume.getUuid());
        makeSave(resume, index);
    }

    @Override
    public void update(Resume resume) {
        int index = isNotExist(resume.getUuid());
        makeUpdate(resume, index);
    }

    @Override
    public void delete(String uuid) {
        int index = isNotExist(uuid);
        makeDelete(uuid, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = isNotExist(uuid);
        return getting(uuid, index);
    }


    protected int isExist(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    protected int isNotExist(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void makeSave(Resume resume, int index);

    protected abstract void makeUpdate(Resume resume, int index);

    protected abstract void makeDelete(String uuid, int index);

    protected abstract Resume getting(String uuid, int index);

}
