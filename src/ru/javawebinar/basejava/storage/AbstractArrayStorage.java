package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

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
    public void updateResume(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    public void saveResume(Resume r, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveResumeToArray(r ,(int) index);
        size++;
    }

    @Override
    public Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    public void deleteResume(Object index) {
        deleteResumeInArray((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> copyAllResume() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }

    protected abstract void saveResumeToArray(Resume resume, int index);

    protected abstract void deleteResumeInArray(int index);
}
