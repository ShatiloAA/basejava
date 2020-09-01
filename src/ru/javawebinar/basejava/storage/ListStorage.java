package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void makeSave(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected void makeUpdate(Resume resume, Object searchKey) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    protected void makeDelete(String uuid, Object searchKey) {
        storage.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected Resume makeGet(String uuid, Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected List<Resume> listOfResumes() {
        return storage;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (Resume resume : storage) {
            if (uuid.equals(resume.getUuid())) {
                return storage.indexOf(resume);
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
