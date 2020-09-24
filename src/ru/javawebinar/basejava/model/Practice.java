package ru.javawebinar.basejava.model;

import java.time.YearMonth;

public class Practice {
    private final String name;
    private final YearMonth startDate;
    private YearMonth endDate;
    private final String description;

    public Practice(String name, YearMonth startDate, YearMonth endDate, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public void setEndDate(YearMonth endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Practice practice = (Practice) o;

        if (!name.equals(practice.name)) return false;
        if (!startDate.equals(practice.startDate)) return false;
        if (endDate != null ? !endDate.equals(practice.endDate) : practice.endDate != null) return false;
        return description.equals(practice.description);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\n" + name + " " + startDate + " - " + (endDate == null ? "now" : endDate) + " " + description + " ";
    }
}
