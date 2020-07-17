package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.out;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage,0, size,null);
        size = 0;
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            out.println("Resume есть в базе");
        } else if (size == STORAGE_LIMIT) {
            out.println("Resume не может быть добавлено, база переполнена!");
        }  else {
            addResume(r);
        }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            out.println("Resume нет в базе");
        } else {
            storage[index] = r;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            delResume(uuid);
        }
        else {
            out.println("Resume нет в базе");
        }
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if(index >= 0){
            return storage[index];
        }
        out.println("нет в базе");
        return null;
    }

    public Resume[] getAll() {
        return Arrays.stream(storage).filter(Objects::nonNull).toArray(Resume[]::new);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void addResume (Resume r);

    protected abstract void delResume (String uuid);
}
