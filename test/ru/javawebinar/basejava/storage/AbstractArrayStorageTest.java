package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";


    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume testResume = new Resume(UUID_1);
        int hashTest = testResume.hashCode();
        storage.update(testResume);
        Assert.assertEquals(storage.get(UUID_1).hashCode(), hashTest);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] testArray = storage.getAll();
        Assert.assertEquals(testArray.length, storage.size());
        for (Resume aTestArray : testArray) {
            Assert.assertEquals(aTestArray, storage.get(aTestArray.getUuid()));
        }
    }

    @Test
    public void save() throws Exception {
        Resume resumeTest = new Resume("uuid4");
        storage.save(resumeTest);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resumeTest, storage.get("uuid4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        int testSize = storage.size();
        storage.delete(UUID_3);
        Assert.assertEquals(testSize - 1, storage.size());
        storage.get(UUID_3);
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

    @Test(expected = ExistStorageException.class)
    public void getExist() throws Exception {
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = StorageException.class)
    public void overFlow() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < 10000; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            if (!(e instanceof StorageException)) {
                Assert.fail();
            }
        }
        storage.save(new Resume());
    }
}