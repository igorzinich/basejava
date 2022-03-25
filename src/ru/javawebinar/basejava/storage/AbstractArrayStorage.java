package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.printf("Error. Resume %s is not found\n", uuid);
        } else {
            storage[index] = resume;
            System.out.printf("Resume %s updated\n", uuid);
        }
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        if (findIndex(uuid) >= 0) {
            System.out.printf("Resume %s already exist\n", uuid);
        } else if (size == STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            saveItem(r);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.printf("Resume %s is not found\n", uuid);
        } else {
            deleteItem(uuid);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.printf("Error. Resume %s is not found\n", uuid);
            return null;
        }
        return storage[index];
    }

    protected abstract void saveItem(Resume r);

    protected abstract void deleteItem(String uuid);

    protected abstract int findIndex(String uuid);

}
