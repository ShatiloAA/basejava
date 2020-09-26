package ru.javawebinar.basejava.model;

public class TextAbstractSection extends AbstractSection {
    private final String description;

    public TextAbstractSection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
