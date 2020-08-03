package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import java.lang.reflect.Field;

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

    @Test(expected = NullPointerException.class)
    public void falseSize() throws Exception {
        Class clazz = Class.forName(storage.getClass().getTypeName());
        Field fieldSize = clazz.getSuperclass().getDeclaredField("size");
        fieldSize.setAccessible(true);
        fieldSize.set(storage, 4);
        storage.save(new Resume("uuid4"));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void incompleteCleaning() throws Exception {
        Class clazz = Class.forName(storage.getClass().getTypeName());
        Field fieldSize = clazz.getSuperclass().getDeclaredField("size");
        fieldSize.setAccessible(true);
        fieldSize.set(storage, 0);
        storage.clear();
        fieldSize.set(storage, 3);
        storage.save(new Resume(UUID_1));
        System.out.println(storage.size());
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
        Assert.assertEquals(resumes.length, storage.size());
        for (Resume resume : resumes) {
            Assert.assertSame(resume, storage.get(resume.getUuid()));
        }
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
    public void getExist() throws Exception {
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = StorageException.class)
    public void checkOverflow() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < 10000; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assert.fail("Возникло исключение раньше переполнения");
        }
        storage.save(new Resume());
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