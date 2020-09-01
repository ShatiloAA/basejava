package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractArrayStorage extends AbstractStorage {

    private static final int STORAGE_LIMIT = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void makeSave(Resume resume, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Resume " + resume.getUuid() + " не может быть добавлено, база переполнена!", resume.getUuid());
        } else {
            addResume(resume, (Integer) index);
            size++;
        }
    }

    @Override
    protected void makeUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void makeDelete(String uuid, Object index) {
        delResume((Integer) index);
        storage[--size] = null;
    }

    @Override
    protected Resume makeGet(String uuid, Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected List<Resume> listOfResumes() {
        return Arrays.asList(storage);
    }

    @Override
    public List<Resume> getAllSorted() {
        return Arrays.stream(storage).filter(Objects::nonNull).sorted(Comparator.comparing(Resume::getFullname).thenComparing(Resume::getUuid)).collect(Collectors.toList());
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    public int size() {
        return size;
    }

    protected abstract void addResume(Resume resume, int index);

    protected abstract void delResume(int index);
}
