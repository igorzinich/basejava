package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected void updateResume(Resume resume, String searchKey) {
        map.replace(searchKey, resume);
    }

    @Override
    protected void saveResume(Resume r, String searchKey) {
        map.put(searchKey, r);
    }

    @Override
    protected Resume getResume(String searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected void deleteResume(String searchKey) {
        map.remove(searchKey);
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
    protected String searchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return map.containsKey(searchKey);
    }

}
