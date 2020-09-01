package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;
import java.util.stream.Collectors;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void makeSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void makeUpdate(Resume resume, Object searchKey) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void makeDelete(String uuid, Object searchKey) {
        storage.remove(uuid);
    }

    @Override
    protected Resume makeGet(String uuid, Object searchKey) {
        return storage.get(uuid);
    }

    @Override
    protected List<Resume> listOfResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.values().stream().sorted(Comparator.comparing(Resume::getFullname).thenComparing(Resume::getUuid)).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }
}
