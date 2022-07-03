package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("MM-yyyy")
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter();

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate localDate) {
        if (localDate == null) return "";
        Month month = localDate.getMonth();
        int year = localDate.getYear();
        return localDate.isAfter(LocalDate.now()) || (month.equals(LocalDate.now().getMonth()) && year == (LocalDate.now().getYear())) ? "Сейчас" : localDate.format(FORMATTER);
    }

    public static LocalDate parse(String date) {
        if (date.equals("") || date.equals("Сейчас")) return NOW;
        return LocalDate.parse(date, FORMATTER);
    }
}