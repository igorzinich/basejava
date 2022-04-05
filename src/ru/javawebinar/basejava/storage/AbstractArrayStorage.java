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
    public void updateResume(Resume resume) {
        String uuid = resume.getUuid();
        storage[(int) searchKey(uuid)] = resume;
        System.out.printf("Resume %s updated\n", uuid);
    }

    @Override
    public void saveResume (Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveResumeToArray(r);
        size++;
    }

    @Override
    public Resume getResume(String uuid) {
        return storage[(int) searchKey(uuid)];
    }

    @Override
    public void deleteResume(String uuid) {
        int index = (int) searchKey(uuid);
        deleteResumeInArray(index);
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
    protected boolean isExist(String uuid) {
        int index = (int) searchKey(uuid);
        return index >= 0;
    }

    @Override
    protected boolean isExist(Resume resume) {
        int index = (int) searchKey(resume.getUuid());
        return index >= 0;
    }

    protected abstract Object searchKey(String uuid);

    protected abstract void saveResumeToArray(Resume r);

    protected abstract void deleteResumeInArray(int index);
}
