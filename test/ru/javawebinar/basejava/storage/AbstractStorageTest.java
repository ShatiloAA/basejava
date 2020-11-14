package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.model.ResumeTestData.fillOutResume;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = new File("C:\\practicalJava\\basejava\\storage");
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume TEST_RESUME_1;
    private static final Resume TEST_RESUME_2;
    private static final Resume TEST_RESUME_3;
    private static final Resume TEST_RESUME_4;

    static {
        TEST_RESUME_1 = fillOutResume(UUID_1, "Celine Dion");
        TEST_RESUME_2 = new Resume(UUID_2, "Astra Rose");
        TEST_RESUME_3 = fillOutResume(UUID_3, "Benny Hill");
        TEST_RESUME_4 = fillOutResume(UUID_4, "John Doe");
    }

    Storage storage;

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
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        assertEquals(3, storage.size());
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume testResume = fillOutResume(UUID_1, "Arnold Schwarzenegger");
        storage.update(testResume);
        Assert.assertEquals(testResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void unsuccessfulUpdate() throws Exception {
        storage.update(TEST_RESUME_4);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> expectedResumes = Arrays.asList(TEST_RESUME_1, TEST_RESUME_2, TEST_RESUME_3);
        expectedResumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        assertEquals(expectedResumes, storage.getAllSorted());
    }

    @Test
    public void save() throws Exception {
        int sizeBeforeSave = storage.size();
        storage.save(TEST_RESUME_4);
        assertEquals(sizeBeforeSave + 1, storage.size());
        assertEquals(TEST_RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(TEST_RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        int sizeBeforeDeletion = storage.size();
        storage.delete(UUID_3);
        assertEquals(sizeBeforeDeletion - 1, storage.size());
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void unsuccessfulDelete() throws Exception {
        storage.delete("uuid4");
    }

    @Test
    public void get() throws Exception {
        assertEquals(TEST_RESUME_1, storage.get(UUID_1));
        assertEquals(TEST_RESUME_2, storage.get(UUID_2));
        assertEquals(TEST_RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}