package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        if (!isExist(resume)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateResume(resume);
    }

    @Override
    public void save(Resume r) {
        if (isExist(r)){
            throw new ExistStorageException(r.getUuid());
        }
        saveResume(r);
    }

    @Override
    public Resume get(String uuid) {
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
       return getResume(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(uuid);
    }

    protected abstract boolean isExist(Resume resume);

    protected abstract boolean isExist(String uuid);

    protected abstract void updateResume(Resume resume);

    protected abstract void saveResume(Resume r);

    protected abstract Resume getResume(String uuid);

    protected abstract void deleteResume(String uuid);

    protected abstract Object searchKey(String uuid);
}
