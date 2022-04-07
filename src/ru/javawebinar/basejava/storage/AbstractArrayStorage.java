package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
        System.out.printf("Resume %s updated\n", resume.getUuid());
    }

    @Override
    public void saveResume(Resume r, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveResumeToArray(r ,(int) searchKey);
        size++;
    }

    @Override
    public Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    public void deleteResume(Object searchKey) {
        deleteResumeInArray((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    protected abstract void saveResumeToArray(Resume resume, int searchKey);

    protected abstract void deleteResumeInArray(int index);
}
