package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void makeSave(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void makeUpdate(Resume resume, int index) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void makeDelete(String uuid, int index) {
        storage.remove(uuid);
    }

    @Override
    protected Resume makeGet(String uuid, int index) {
        return storage.get(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getSearchKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return 1;
        }
        return -1;
    }
}
