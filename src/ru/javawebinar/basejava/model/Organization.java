package ru.javawebinar.basejava.model;

import java.util.List;

public class Organization {
    private final String title;
    private final Link homepage;
    private final List<Experience> experiences;

    public Organization(String title, String url, List<Experience> experiences) {
        this.title = title;
        this.homepage = new Link(title, url);
        this.experiences = experiences;
    }

    public Link getHomepage() {
        return homepage;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    @Override
    public String toString() {
        return  title + ", " + homepage +
                ", " + experiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization organization = (Organization) o;

        if (!title.equals(organization.title)) return false;
        if (homepage != null ? !homepage.equals(organization.homepage) : organization.homepage != null) return false;
        return experiences.equals(organization.experiences);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (homepage != null ? homepage.hashCode() : 0);
        result = 31 * result + experiences.hashCode();
        return result;
    }

    //return "\n" + name + " " + startDate + " - " + (endDate == null ? "now" : endDate) + " " + description + " ";
}
