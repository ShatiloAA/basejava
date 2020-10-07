package ru.javawebinar.basejava.model;

import java.util.List;

public class Practice {
    private final String title;
    private final Link homepage;
    private final List<Period> periods;

    public Practice(String title, String url, List<Period> periods) {
        this.title = title;
        this.homepage = new Link(title, url);
        this.periods = periods;
    }

    public Link getHomepage() {
        return homepage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        return  title + ", " + homepage +
                ", " + periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Practice practice = (Practice) o;

        if (!title.equals(practice.title)) return false;
        if (homepage != null ? !homepage.equals(practice.homepage) : practice.homepage != null) return false;
        return periods.equals(practice.periods);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (homepage != null ? homepage.hashCode() : 0);
        result = 31 * result + periods.hashCode();
        return result;
    }

    //return "\n" + name + " " + startDate + " - " + (endDate == null ? "now" : endDate) + " " + description + " ";
}
