package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void makeSave(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void makeUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void makeDelete(String uuid, int index) {
        storage.remove(index);
    }

    @Override
    protected Resume getting(String uuid, int index) {
        return storage.get(index);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(Resume[]::new);
    }

    @Override
    protected int getIndex(String uuid) {
        for (Resume resume : storage) {
            if (uuid.equals(resume.getUuid())) {
                return storage.indexOf(resume);
            }
        }
        return -1;
    }

}
