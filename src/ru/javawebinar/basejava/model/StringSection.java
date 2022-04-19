package ru.javawebinar.basejava.model;

import java.util.Objects;

public class StringSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringSection that = (StringSection) o;

        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
