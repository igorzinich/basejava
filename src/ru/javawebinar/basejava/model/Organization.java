package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Organization {
    private String name;
    private Link link;
    private LocalDate startDate;
    private LocalDate endDate;
    private String head;
    private String description;

    public Organization(String name, Link link, LocalDate startDate, LocalDate endDate, String head, String description) {
        this.name = name;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.head = head;
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nOrganization" +
                "\nname=" + name +
                "\nlink=" + link +
                "\nstartDate=" + startDate +
                "\nendDate=" + endDate +
                "\nhead=" + head +
                "\ndescription='" + description;
    }
}
