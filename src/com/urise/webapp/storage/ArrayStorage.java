package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (getSimilar(resume.getUuid()) == -1) {
            System.out.printf("Error. Resume %s is not found\n", resume.getUuid());
        } else {
            storage[getSimilar(resume.getUuid())] = resume;
            System.out.printf("Resume %s updated\n", resume.getUuid());
        }
    }

    public void save(Resume r) {
        if (getSimilar(r.getUuid()) != -1) {
            System.out.printf("Resume %s always exists\n", r.getUuid());
        } else if (size == storage.length) {
            System.out.println("Storage is full");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        if (getSimilar(uuid) == -1) {
            System.out.printf("Error. Resume %s is not found\n", uuid);
        } else {
            return storage[getSimilar(uuid)];
        }
        return null;
    }

    public void delete(String uuid) {
        if (getSimilar(uuid) == -1) {
            System.out.printf("Resume %s is not found\n", uuid);
        } else {
            storage[getSimilar(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getSimilar(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
