package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorageTest {

    Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume TEST_RESUME_1;
    private static final Resume TEST_RESUME_2;
    private static final Resume TEST_RESUME_3;
    private static final Resume TEST_RESUME_4;

    static {
        TEST_RESUME_1 = new Resume(UUID_1, "Celine Dion");
        TEST_RESUME_2 = new Resume(UUID_2, "Astra Rose");
        TEST_RESUME_3 = new Resume(UUID_3, "Benny Hill");
        TEST_RESUME_4 = new Resume(UUID_4, "John Doe");
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(TEST_RESUME_1);
        storage.save(TEST_RESUME_2);
        storage.save(TEST_RESUME_3);
    }

    @Test
    public void successfulSize() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        Assert.assertEquals(3, storage.size());
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume testResume = new Resume(UUID_1, "Arnold Schwarzenegger");
        storage.update(testResume);
        Assert.assertSame(testResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void unsuccessfulUpdate() throws Exception {
        storage.update(TEST_RESUME_4);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> expectedResumes = Arrays.asList(TEST_RESUME_1, TEST_RESUME_2, TEST_RESUME_3);
        expectedResumes.sort(Comparator.comparing(Resume::getFullName));
        Assert.assertEquals(expectedResumes, storage.getAllSorted());
    }

    @Test
    public void save() throws Exception {
        int sizeBeforeSave = storage.size();
        storage.save(TEST_RESUME_4);
        Assert.assertEquals(sizeBeforeSave + 1, storage.size());
        Assert.assertEquals(TEST_RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(TEST_RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        int sizeBeforeDeletion = storage.size();
        storage.delete(UUID_3);
        Assert.assertEquals(sizeBeforeDeletion - 1, storage.size());
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void unsuccessfulDelete() throws Exception {
        storage.delete("uuid4");
    }

    @Test
    public void get() throws Exception {
        Resume resumeTest = new Resume(UUID_3, TEST_RESUME_3.getFullName());
        Assert.assertEquals(resumeTest, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}