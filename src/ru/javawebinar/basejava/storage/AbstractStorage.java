package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class AbstractStorage implements Storage {
    @Override
    public void clear() {

    }

    @Override
    public void save(Resume r) {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
