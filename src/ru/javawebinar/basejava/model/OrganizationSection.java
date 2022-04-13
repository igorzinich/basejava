package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<Organization> list = new ArrayList<>();

    public OrganizationSection(List<Organization> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public List<Organization> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "list=" + list +
                '}';
    }
}
