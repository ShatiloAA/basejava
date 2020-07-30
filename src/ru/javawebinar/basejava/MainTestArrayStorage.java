package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ArrayStorage;
import ru.javawebinar.basejava.storage.SortedArrayStorage;
import ru.javawebinar.basejava.storage.Storage;

import static java.lang.System.*;

/**
 * Test for your ru.javawebinar.basejava.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new ArrayStorage();
    static final Storage SORTED_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        //Test unsorted storage
        out.println(">>> Test unsorted storage");
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        out.println("Size: " + ARRAY_STORAGE.size());
        out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll(ARRAY_STORAGE);
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll(ARRAY_STORAGE);
        ARRAY_STORAGE.update(r2);
        printAll(ARRAY_STORAGE);
        ARRAY_STORAGE.clear();
        printAll(ARRAY_STORAGE);
        out.println("Size: " + ARRAY_STORAGE.size());
        out.println("- - - - - - - - - - - - -");
        //Test sorted storage
        out.println(">>> Test sorted storage");
        SORTED_STORAGE.save(r1);
        SORTED_STORAGE.save(r3);
        SORTED_STORAGE.save(r2);
        out.println("Get r1: " + SORTED_STORAGE.get(r1.getUuid()));
        out.println("Size: " + SORTED_STORAGE.size());
        out.println("Get dummy: " + SORTED_STORAGE.get("dummy"));

        printAll(SORTED_STORAGE);
        SORTED_STORAGE.delete(r1.getUuid());
        printAll(SORTED_STORAGE);
        SORTED_STORAGE.update(r2);
        printAll(SORTED_STORAGE);
        SORTED_STORAGE.clear();
        printAll(SORTED_STORAGE);
        out.println("Size: " + SORTED_STORAGE.size());
        out.println("- - - - - - - - - - - - -");
    }

    static void printAll(Storage storage) {
        out.println("\nGet All");
        for (Resume r : storage.getAll()) {
            out.println(r);
        }
    }
}
