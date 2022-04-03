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
    protected void clearStorage() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateResume(Resume resume, int index) {
            storage[index] = resume;
            System.out.printf("Resume %s updated\n", resume.getUuid());
    }

    @Override
    protected void saveResume(Resume r, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            saveResumeToArray(r, index);
            size++;
        }
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected void deleteResume(int index) {
        deleteResumeInArray(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume[] getAllResumes() {
        return Arrays.copyOf(storage, size);
    }

    protected int sizeOfStorage() {
        return size;
    }

    protected abstract void saveResumeToArray(Resume r, int index);

    protected abstract void deleteResumeInArray(int index);

    protected abstract int findIndex(String index);

}
