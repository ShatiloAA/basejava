package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorageTest {
    //protected for accessibility in overriden inherited tests
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume TEST_RESUME_1;
    private static final Resume TEST_RESUME_2;
    private static final Resume TEST_RESUME_3;

    static {
        TEST_RESUME_1 = new Resume(UUID_1);
        TEST_RESUME_2 = new Resume(UUID_2);
        TEST_RESUME_3 = new Resume(UUID_3);
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
        Resume testResume = new Resume(UUID_1);
        storage.update(testResume);
        Assert.assertSame(testResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void unsuccessfulUpdate() throws Exception {
        Resume testResume = new Resume("uuid4");
        storage.update(testResume);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes = storage.getAll();
        Resume[] testResumes = new Resume[]{TEST_RESUME_1, TEST_RESUME_2, TEST_RESUME_3};
        Assert.assertEquals(resumes.length, storage.size());
        Assert.assertArrayEquals(testResumes, resumes);
    }

    @Test(expected = NullPointerException.class)
    public void unsuccessfulGetAll() throws Exception {
        storage = null;
        getAll();
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resume, storage.get("uuid4"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_3));
    }

    //relevant for Array implementations
    @Test
    public void checkOverflow() throws Exception {
        System.out.println("Тест актуален только для реализации с массивом");
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
        Resume resumeTest = new Resume(UUID_3);
        Assert.assertEquals(resumeTest, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}