package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void save(Resume r);

    void update(Resume r);

    void delete(String uuid);

    Resume get(String uuid);

    List<Resume> getAllSorted();

    int size();
}
