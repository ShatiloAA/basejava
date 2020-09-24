package ru.javawebinar.basejava.model;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) throws IOException {
        Resume testResume = new Resume("1", "Григорий Кислин");

        testResume.setContact(TELEPHONE, "+7(921) 855-0482");
        testResume.setContact(SKYPE, "grigory.kislin");
        testResume.setContact(EMAIL, "gkislin@yandex.ru");
        testResume.setContact(LINKEDIN, "Профиль LinkedIn");
        testResume.setContact(GITHUB, "Профиль GitHub");
        testResume.setContact(STACKOVERFLOW, "Профиль Stackoverflow");
        testResume.setContact(HOMEPAGE, "Домашняя страница");

        testResume.setSection(PERSONAL, new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям") {
        });
        testResume.setSection(OBJECTIVE, new TextSection("Аналитический склад ума, сильная логика, " +
                "креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP." +
                "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery." +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA," +
                " Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия" +
                " слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. " +
                "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievementsList.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        testResume.setSection(ACHIEVEMENT, new ListSection(achievementsList));

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualificationsList.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualificationsList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        testResume.setSection(QUALIFICATIONS, new ListSection(qualificationsList));

        List<Practice> experienceList = new ArrayList<>();
        experienceList.add(new Practice(
                "Java Online Projects",
                YearMonth.of(2013, 10),
                null,
                "Автор проекта.\n" +
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experienceList.add(new Practice(
                "Wrike",
                YearMonth.of(2014, 10),
                YearMonth.of(2016, 1),
                "Старший разработчик (backend)\n" +
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experienceList.add(new Practice(
                "Luxoft (Deutsche Bank)",
                YearMonth.of(2010, 12),
                YearMonth.of(2012, 4),
                "Ведущий программист\n" +
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, " +
                        "Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения " +
                        "для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. " +
                        "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        testResume.setSection(EXPERIENCE, new PracticeSection(experienceList));

        List<Practice> educationList = new ArrayList<>();
        educationList.add(new Practice(
                "Coursera",
                YearMonth.of(2013, 03),
                YearMonth.of(2013, 05),
                "\"Functional Programming Principles in Scala\" by Martin Odersky"
        ));
        educationList.add(new Practice(
                "Luxoft",
                YearMonth.of(2013, 03),
                YearMonth.of(2013, 05),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""
        ));
        testResume.setSection(EDUCATION, new PracticeSection(educationList));

        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType + " " + testResume.getContact(contactType));
        }

        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType + " " + testResume.getSection(sectionType));
        }

    }
}
