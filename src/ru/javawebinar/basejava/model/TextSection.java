package ru.javawebinar.basejava.model;

public class TextSection extends Section {
    private final String description;

    public TextSection(String description) {
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
