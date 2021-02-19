package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;

import java.io.File;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.model.ResumeTestData.fillOutResume;
import static ru.javawebinar.basejava.model.ResumeTestData.fillOutUpdate;

public abstract class AbstractStorageTest {

    static final File STORAGE_DIR = Config.get().getStorageDir();

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final Resume TEST_RESUME_1;
    private static final Resume TEST_RESUME_2;
    private static final Resume TEST_RESUME_3;
    private static final Resume TEST_RESUME_4;

    static {
        TEST_RESUME_1 = new Resume(UUID_1, "Celine Dion");
        TEST_RESUME_2 = fillOutResume(UUID_2, "Astra Rose");
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
    public void size() throws Exception {
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
        Resume testResume = fillOutUpdate(UUID_1, "Arnold Schwarzenegger");
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
        //assertEquals(TEST_RESUME_4.getContacts(), storage.get(UUID_4).getContacts());
        //assertEquals(TEST_RESUME_4.getSections(), storage.get(UUID_4).getSections());
        /*for(Map.Entry<SectionType, Section> map : TEST_RESUME_4.getSections().entrySet()) {
            if(!(map.getValue().equals(storage.get(UUID_4).getSection(map.getKey())))) {
                System.out.println("пойман за руку, как дешевка! " + map.getKey());
            }
        }*/
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