package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;

import java.io.File;
import java.time.Month;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.util.DateUtil.NOW;

public abstract class AbstractStorageTest {

    Storage storage;

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
        /*TEST_RESUME_1 = fillOutResume(UUID_1, "Celine Dion");
        TEST_RESUME_2 = fillOutResume(UUID_2, "Astra Rose");
        TEST_RESUME_3 = fillOutResume(UUID_3, "Benny Hill");
        TEST_RESUME_4 = fillOutResume(UUID_4, "John Doe");*/

        TEST_RESUME_1 = new Resume(UUID_1, "Name1");
        TEST_RESUME_2 = new Resume(UUID_2, "Name2");
        TEST_RESUME_3 = new Resume(UUID_3, "Name3");
        TEST_RESUME_4 = new Resume(UUID_4, "Name4");

        TEST_RESUME_1.addContact(ContactType.EMAIL, "mail1@ya.ru");
        TEST_RESUME_1.addContact(ContactType.TELEPHONE, "11111");
        TEST_RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        TEST_RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        TEST_RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        TEST_RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        TEST_RESUME_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position("position1", DateUtil.of(2005, Month.JANUARY), NOW, "content1"),
                                new Organization.Position("position2", DateUtil.of(2001, Month.MARCH), DateUtil.of(2005, Month.JANUARY), "content2"))));
        TEST_RESUME_1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position("aspirant", DateUtil.of(1996, Month.MARCH), DateUtil.of(2001, Month.JANUARY), null),
                                new Organization.Position("student", DateUtil.of(2001, Month.MARCH), DateUtil.of(2005, Month.JANUARY), "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
        TEST_RESUME_2.addContact(ContactType.SKYPE, "skype2");
        TEST_RESUME_2.addContact(ContactType.TELEPHONE, "22222");
        TEST_RESUME_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position("position1", DateUtil.of(2005, Month.JANUARY), NOW, "content1"))));
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
        //Resume testResume = fillOutResume(UUID_1, "Arnold Schwarzenegger");
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        //Assert.assertEquals(testResume, storage.get(UUID_1));
        Assert.assertEquals(newResume, storage.get(UUID_1));
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
        //Resume resumeTest = fillOutResume(UUID_3, "Benny Hill");
        assertEquals(TEST_RESUME_1, storage.get(UUID_1));
        assertEquals(TEST_RESUME_2, storage.get(UUID_2));
        assertEquals(TEST_RESUME_3, storage.get(UUID_3));
        //Assert.assertEquals(resumeTest, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}