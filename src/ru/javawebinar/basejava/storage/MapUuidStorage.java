package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        map.replace((String) searchKey, resume);
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        map.put((String) searchKey, r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return map.get((String) searchKey);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        map.remove((String) searchKey);
    }

    @Override
    protected List<Resume> copyAllResume() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected Object searchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey((String) searchKey);
    }

}
