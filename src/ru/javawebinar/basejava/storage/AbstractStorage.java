package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public abstract void clear();

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            updateResume(resume, index);
        }
    }

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        saveResume(r, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return getResume(index);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
        }
    }

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    protected abstract int findIndex(String uuid);

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void saveResume(Resume r, int index);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(int index);
}
