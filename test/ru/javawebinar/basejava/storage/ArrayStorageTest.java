package ru.javawebinar.basejava.storage;

import org.junit.Before;
import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ArrayStorageTest extends AbstractArrayStorageTest{
    public ArrayStorageTest() {
        super(new ArrayStorage());
    }


}
