package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbstractStorageTest {

    protected Storage storage;
    protected final static String PATH = "C:\\basejava\\storage";
    protected final static File STORAGE_DIR = new File(PATH);
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME1 = "Resume1";
    private static final String FULLNAME2 = "Resume2";
    private static final String FULLNAME3 = "Resume3";
    private static final String FULLNAME4 = "Resume4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;
    private static final String UUID_NOT_EXIST = "dummy";

    static {
        RESUME_1 = new Resume(UUID_1, FULLNAME1);
        RESUME_2 = new Resume(UUID_2, FULLNAME2);
        RESUME_3 = new Resume(UUID_3, FULLNAME3);
        RESUME_4 = new Resume(UUID_4, FULLNAME4);

        RESUME_1.addContact(ContactType.PHONE, "323232");
        RESUME_1.addContact(ContactType.MAIL, "mail@mail.ru");
        RESUME_1.addContact(ContactType.GITHUB, "GitHub.com/People");
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Личные качества"));
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Позиция"));
       // RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Достижения", "Достижения_2", "Достижения_3"));
       // RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Квалификация", "Квалификация_2", "Квалификация_3"));
       /* RESUME_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Period(2005, Month.JANUARY, "position1", "content1"),
                                new Period(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        RESUME_1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", "www.ru",
                                new Period(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", "test"),
                                new Period(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));*/
        /*RESUME_1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("Content", "www.Alcatel.ru",
                        new Period(LocalDate.of(1997, Month.JANUARY,15),
                                LocalDate.of(1999, Month.SEPTEMBER,10),"title","text"))));
        RESUME_1.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("University", "www.Unity.ru",
                        new Period(LocalDate.of(1987, Month.SEPTEMBER,01),
                                LocalDate.of(1993,Month.JULY,1), "St.Petersnurg","ingeneer"),
                        new Period(LocalDate.of(1993, Month.SEPTEMBER,5),
                                LocalDate.of(1996, Month.JULY,14), "St. Petersburg","Aspirant"))));
        */
        RESUME_2.addContact(ContactType.PHONE, "555555");
        RESUME_2.addContact(ContactType.MAIL, "babl@mail.ru");
        RESUME_2.addSection(SectionType.PERSONAL, new TextSection("Личные качества"));
        RESUME_2.addSection(SectionType.OBJECTIVE, new TextSection("Позиция"));
      //  RESUME_2.addSection(SectionType.ACHIEVEMENT, new ListSection("Достижения", "Достижения_2", "Достижения_3"));
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    public void assertSize(int expectedSize) {
        Assert.assertEquals(expectedSize, storage.size());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    public void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }


    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(3,list.size());
        //assertSize(3);
        Assert.assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, "New Name");
        storage.update(resume);
        Assert.assertTrue(resume.equals(storage.get(UUID_1)));
    }


    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST, ""));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1, FULLNAME1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        assertGet(RESUME_1);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> resumes = new ArrayList<>();
        Assert.assertArrayEquals(resumes.toArray(),
                storage.getAllSorted().toArray());
    }
}