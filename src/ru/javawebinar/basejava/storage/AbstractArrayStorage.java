package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage extends AbstractStorage {

    private static final int STORAGE_LIMIT = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void makeSave(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Resume " + resume.getUuid() + " не может быть добавлено, база переполнена!", resume.getUuid());
        } else {
            addResume(resume, index);
            size++;
        }
    }

    @Override
    protected void makeUpdate(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void makeDelete(String uuid, int index) {
        delResume(uuid, index);
        storage[--size] = null;
    }

    @Override
    protected Resume makeGet(String uuid, int index) {
        return storage[index];
    }

    public Resume[] getAll() {
        return Arrays.stream(storage).filter(Objects::nonNull).toArray(Resume[]::new);
    }

    public int size() {
        return size;
    }

    protected abstract int getSearchKey(String uuid);

    protected abstract void addResume(Resume resume, int index);

    protected abstract void delResume(String uuid, int index);
}
