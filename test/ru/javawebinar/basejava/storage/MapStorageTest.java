package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes = storage.getAll();
        Arrays.sort(resumes);
        Resume[] testResumes = new Resume[]{new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3")};
        Assert.assertArrayEquals(testResumes, resumes);
    }
}