package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static java.lang.System.out;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void addResume(Resume r) {
        int index = -(getIndex(r.getUuid())) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
        size++;
    }

    @Override
    protected void delResume(String uuid) {
        int index = getIndex(uuid);
        int length = size - (index == 0 ? 1 : index);
        System.arraycopy(storage, index + 1, storage, index, length);
        storage[--size] = null;
        }
}
