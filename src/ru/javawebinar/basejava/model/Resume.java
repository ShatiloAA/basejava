package ru.javawebinar.basejava.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private String fullname;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
        uuid = UUID.randomUUID().toString();
    }

    public Resume(String uuid, String fullname) {
        this.uuid = uuid;
        this.fullname = fullname;
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setContact(ContactType contactType, String contactValue) {
        contacts.put(contactType, contactValue);
    }

    public Section getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void setSection(SectionType sectionType, Section sectionValue) {
        sections.put(sectionType, sectionValue);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullname() {
        return fullname;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
