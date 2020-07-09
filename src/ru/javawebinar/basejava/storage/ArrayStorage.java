package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.*;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int counter = 0;

    public void clear() {
        Arrays.fill(storage,null);
        counter = 0;
    }

    public void save(Resume r) {
        if (getIndex(r.getUuid()) >= 0) {
            printWarning("уже есть");
        } else if (counter == storage.length) {
            printWarning("не может быть добавлено, база переполнена!");
        }  else {
            storage[counter++] = r;
        }
    }

    public void update(String uuid, String newValue) {
        if (getIndex(uuid) >= 0)  {
            get(uuid).setUuid(newValue);
        }
    }

    public Resume get(String uuid) {
        if(getIndex(uuid) >= 0){
            return storage[getIndex(uuid)];
        }
        printWarning("нет нет в базе");
        return null;
    }

    public void delete(String uuid) {
        if (getIndex(uuid) >= 0) {
            storage[getIndex(uuid)] = storage[--counter];
            storage[counter] = null;
        }
        else {
            printWarning("нет в базе");
        }
    }

    public Resume[] getAll() {
        return Arrays.stream(storage).filter(Objects::nonNull).toArray(Resume[]::new);
    }

    public int size() {
        return counter;
    }

    private int getIndex (String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid() == uuid) {
                return i;
            }
        }
        return -1;
    }

    private void printWarning(String warning) {
        out.println("Resume " + warning);
    }
}
