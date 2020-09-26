package ru.javawebinar.basejava.model;

import java.util.List;

public class PracticeAbstractSection extends AbstractSection {
    private final List<Practice> practices;

    public PracticeAbstractSection(List<Practice> practices) {
        this.practices = practices;
    }

    public List<Practice> getPractices() {
        return practices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PracticeAbstractSection that = (PracticeAbstractSection) o;

        return practices.equals(that.practices);
    }

    @Override
    public int hashCode() {
        return practices.hashCode();
    }

    @Override
    public String toString() {
        return practices.toString();
    }
}
