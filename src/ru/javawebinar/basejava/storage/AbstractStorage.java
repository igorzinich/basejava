package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void clear() {
        clearStorage();
    }

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
        } else {
            saveResume(r, index);
        }
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
    public Resume[] getAll() {
        return getAllResumes();
    }

    @Override
    public int size() {
        return sizeOfStorage();
    }

    protected abstract int findIndex(String uuid);

    protected abstract void clearStorage();

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void saveResume(Resume r, int index);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(int index);

    protected abstract Resume[] getAllResumes();

    protected abstract int sizeOfStorage();

}
