package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;

public interface SQLExecutor {
    PreparedStatement execute(PreparedStatement ps);
}
