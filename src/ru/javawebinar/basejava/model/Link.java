package ru.javawebinar.basejava.model;

public class Link {
    private String name;

    public Link(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
