package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import static java.time.Month.*;
import static ru.javawebinar.basejava.util.DateUtil.NOW;
import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static Resume fillOutResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContact(TELEPHONE, "+7(921) 855-0482");
        resume.setContact(SKYPE, "grigory.kislin");
        resume.setContact(EMAIL, "gkislin@yandex.ru");
        resume.setContact(LINKEDIN, "Профиль LinkedIn");
        resume.setContact(GITHUB, "Профиль GitHub");
        resume.setContact(STACKOVERFLOW, "Профиль Stackoverflow");
        resume.setContact(HOMEPAGE, "Домашняя страница");

        resume.setSection(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.setSection(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.");
        achievementsList.add("Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.setSection(ACHIEVEMENT, new ListSection(achievementsList));


        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3)");
        qualificationsList.add("OC4J");
        qualificationsList.add("JBoss");
        resume.setSection(QUALIFICATIONS, new ListSection(qualificationsList));
        List<Organization> experienceList = new ArrayList<>();
        experienceList.add(new Organization(
                "Java Online Projects",
                "javaops.ru",
                new Organization.Position(
                        "Автор проекта", DateUtil.of(2013, OCTOBER),
                        NOW,
                        "Создание, организация и проведение Java онлайн проектов и стажировок.")));
        resume.setSection(EXPERIENCE, new OrganizationSection(experienceList));

        List<Organization> educationList = new ArrayList<>();
        educationList.add(new Organization(
                "Coursera",
                "Coursera.com",
                new Organization.Position(
                        "Студент", DateUtil.of(2013, MARCH),
                        DateUtil.of(2013, MAY),
                        "'Functional Programming Principles in Scala' by Martin Odersky")));
        resume.setSection(EDUCATION, new OrganizationSection(educationList));
        return resume;
    }

    public static Resume fillOutUpdate (String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.setContact(TELEPHONE, "+7(921) 855-0483");
        resume.setContact(SKYPE, "grigory.kisline");
        resume.setContact(EMAIL, "gkislin@mail.ru");
        resume.setContact(LINKEDIN, "Профиль LinkedIn1");
        resume.setContact(GITHUB, "Профиль GitHub1");
        resume.setContact(STACKOVERFLOW, "Профиль Stackoverflow1");
        resume.setContact(HOMEPAGE, "Домашняя страница1");

        resume.setSection(PERSONAL, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.setSection(OBJECTIVE, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.");
        achievementsList.add("Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.setSection(ACHIEVEMENT, new ListSection(achievementsList));


        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3)");
        qualificationsList.add("OC4J");
        qualificationsList.add("JBoss");
        resume.setSection(QUALIFICATIONS, new ListSection(qualificationsList));
/*
        List<Organization> experienceList = new ArrayList<>();
        experienceList.add(new Organization(
                "Java Online Projects",
                "javaops.ru",
                new Organization.Position(
                        "Java Online Projects", DateUtil.of(2013, OCTOBER),
                        NOW,
                        "Автор проекта. Создание, организация и проведение Java онлайн проектов и стажировок.")));
        resume.setSection(EXPERIENCE, new OrganizationSection(experienceList));

        List<Organization> educationList = new ArrayList<>();
        educationList.add(new Organization(
                "Coursera",
                "Coursera.com",
                new Organization.Position(
                        "Coursera", DateUtil.of(2013, MARCH),
                        DateUtil.of(2013, MAY),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky")));
        resume.setSection(EDUCATION, new OrganizationSection(educationList));
*/
        return resume;
    }

    public static void main(String[] args) {
        Resume resume = fillOutResume("1", "Georgy Kislin");
        for (ContactType contactType : ContactType.values()) {
            System.out.println(resume.getContact(contactType));
        }
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(resume.getSection(sectionType));
        }
    }
}
