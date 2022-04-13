package ru.javawebinar.basejava.model;

import java.util.Objects;

public class StringSection extends AbstractSection {
    private final String description;

    public StringSection(String description) {
        Objects.requireNonNull(description, "description must not be null");
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description + "\n";
    }
}
