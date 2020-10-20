package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Experience implements Serializable {

    private final LocalDate startDate;
    private LocalDate endDate;
    private final String description;

    public Experience(LocalDate startDate, LocalDate endDate, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(description, "description must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience experience = (Experience) o;

        if (!startDate.equals(experience.startDate)) return false;
        if (!endDate.equals(experience.endDate)) return false;
        return description.equals(experience.description);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return startDate.format(DateTimeFormatter.ofPattern("YYYY/MM")) +
                " - " + endDate.format(DateTimeFormatter.ofPattern("YYYY/MM")) +
                ", " + description + '\n';
    }
}
