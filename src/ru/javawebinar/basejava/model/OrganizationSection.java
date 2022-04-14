package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private final List<Organization> listOrganizations;

    public OrganizationSection(List<Organization> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.listOrganizations = list;
    }

    public List<Organization> getListOrganizations() {
        return listOrganizations;
    }

    @Override
    public String toString() {
        return listOrganizations.toString() + "\n";
    }
}
