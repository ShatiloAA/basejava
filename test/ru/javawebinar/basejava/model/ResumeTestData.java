package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.*;
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

        resume.setSection(PERSONAL, new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям") {
        });
        resume.setSection(OBJECTIVE, new TextSection("Аналитический склад ума, сильная логика, " +
                "креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.setSection(ACHIEVEMENT, new ListSection(achievementsList));


        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.setSection(QUALIFICATIONS, new ListSection(qualificationsList));

        List<Organization> experienceList = new ArrayList<>();
        experienceList.add(new Organization(
                "Java Online Projects",
                "javaops.ru",
                new Organization.Position(
                        "Java Online Projects", DateUtil.of(2013, OCTOBER),
                        LocalDate.now(),
                        "Автор проекта.\n" +
                                "Создание, организация и проведение Java онлайн проектов и стажировок.")));
        experienceList.add(new Organization(
                "Wrike",
                "wrike.com",
                new Organization.Position(
                        "Wrike", DateUtil.of(2016, JANUARY),
                        DateUtil.of(2014, OCTOBER),
                        "Старший разработчик (backend)\n" +
                                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));
        resume.setSection(EXPERIENCE, new OrganizationSection(experienceList));

        List<Organization> educationList = new ArrayList<>();
        educationList.add(new Organization(
                "Coursera",
                "Coursera.com",
                new Organization.Position(
                        "Coursera", DateUtil.of(2013, MARCH),
                        DateUtil.of(2013, MAY),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky")));
        educationList.add(new Organization(
                "Luxoft",
                "Luxoft.com",
                new Organization.Position(
                        "Luxoft", DateUtil.of(2014, FEBRUARY),
                        DateUtil.of(2014, APRIL),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"")));
        resume.setSection(EDUCATION, new OrganizationSection(educationList));

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
