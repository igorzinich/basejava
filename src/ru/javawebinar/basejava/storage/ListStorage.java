package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        storage.set((int) searchKey(resume.getUuid()), resume);
        System.out.printf("Resume %s updated\n", resume.getUuid());
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove(storage.get((Integer) searchKey));
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
