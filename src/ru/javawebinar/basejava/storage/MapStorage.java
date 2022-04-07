package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        map.replace(resume.getUuid(), resume);
        System.out.printf("Resume %s updated\n", resume.getUuid());
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void deleteResume(Object searchKey) {
        map.remove(((Resume) searchKey).getUuid());
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected Object searchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

}
