package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.*;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void addResume(Resume r) {
        storage[size++] = r;
    }

    @Override
    protected void delResume(String uuid) {
        storage[getIndex(uuid)] = storage[--size];
        storage[size] = null;
    }
}
