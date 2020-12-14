package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.*;
import static ru.javawebinar.basejava.util.DateUtil.NOW;
import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static Resume fillOutResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        /*resume.addContact(TELEPHONE, "+7(921) 855-0482");
        resume.addContact(SKYPE, "grigory.kislin");
        resume.addContact(EMAIL, "gkislin@yandex.ru");
        resume.addContact(LINKEDIN, "Профиль LinkedIn");
        resume.addContact(GITHUB, "Профиль GitHub");
        resume.addContact(STACKOVERFLOW, "Профиль Stackoverflow");
        resume.addContact(HOMEPAGE, "Домашняя страница");

        resume.addSection(PERSONAL, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(OBJECTIVE, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.addSection(ACHIEVEMENT, new ListSection(achievementsList));


        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        resume.addSection(QUALIFICATIONS, new ListSection(qualificationsList));

        List<Organization> experienceList = new ArrayList<>();
        experienceList.add(new Organization(
                "Java Online Projects",
                "javaops.ru",
                new Organization.Position(
                        "Java Online Projects", DateUtil.of(2013, OCTOBER),
                        NOW,
                        "Автор проекта. Создание, организация и проведение Java онлайн проектов и стажировок.")));
        resume.addSection(EXPERIENCE, new OrganizationSection(experienceList));

        List<Organization> educationList = new ArrayList<>();
        educationList.add(new Organization(
                "Coursera",
                "Coursera.com",
                new Organization.Position(
                        "Coursera", DateUtil.of(2013, MARCH),
                        DateUtil.of(2013, MAY),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky")));
        resume.addSection(EDUCATION, new OrganizationSection(educationList));
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
