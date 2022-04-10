package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void updateResume(Resume resume, Object index) {
        storage.set((int) searchKey(resume.getUuid()), resume);
    }

    @Override
    protected void saveResume(Resume r, Object index) {
        storage.add(r);
    }

    @Override
    protected Resume getResume(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void deleteResume(Object index) {
        storage.remove(storage.get((Integer) index));
    }

    @Override
    protected List<Resume> copyAllResume() {
        return new ArrayList<>(storage);
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

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }
}
