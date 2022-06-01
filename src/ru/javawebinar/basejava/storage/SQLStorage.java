package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.TransactionExecute;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLStorage implements Storage {
    public final TransactionExecute transactionExecute;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        transactionExecute = new TransactionExecute(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        transactionExecute.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        transactionExecute.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        transactionExecute.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)");
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
//            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return transactionExecute.execute("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.execute("");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        transactionExecute.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return transactionExecute.execute("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return transactionExecute.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        });
    }
}
