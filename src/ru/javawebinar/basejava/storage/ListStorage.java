package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isExist(Resume resume) {
        int index = (int) searchKey(resume.getUuid());
        return index >= 0;
    }

    @Override
    protected boolean isExist(String uuid) {
        int index = (int) searchKey(uuid);
        return index >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateResume(Resume resume) {
        storage.set((int) searchKey(resume.getUuid()), resume);
        System.out.printf("Resume %s updated\n", resume.getUuid());
    }

    @Override
    protected void saveResume(Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume getResume(String uuid) {
        return storage.get((Integer) searchKey(uuid));
    }

    @Override
    protected void deleteResume(String uuid) {
        storage.remove(storage.get((Integer) searchKey(uuid)));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object searchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
