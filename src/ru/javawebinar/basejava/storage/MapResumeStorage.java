package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    Map<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        map.replace(resume.getUuid(), resume);
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
    protected Object searchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<Resume> copyAllResume() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
