package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    private static final int STORAGE_LIMIT = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void makeSave(Resume resume, Integer index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Resume " + resume.getUuid() + " не может быть добавлено, база переполнена!", resume.getUuid());
        } else {
            addResume(resume, index);
            size++;
        }
    }

    @Override
    protected void makeUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected void makeDelete(String uuid, Integer index) {
        delResume(index);
        storage[--size] = null;
    }

    @Override
    protected Resume makeGet(Integer index) {
        return storage[index];
    }

    @Override
    protected List<Resume> listOfResumes() {
        return Arrays.asList(storage);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    public int size() {
        return size;
    }

    protected abstract void addResume(Resume resume, int index);

    protected abstract void delResume(int index);
}
