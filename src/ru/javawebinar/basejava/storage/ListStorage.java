package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void makeSave(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    @Override
    protected void makeUpdate(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }

    @Override
    protected void makeDelete(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    protected Resume makeGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected List<Resume> returnResumeList() {
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
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }
}
