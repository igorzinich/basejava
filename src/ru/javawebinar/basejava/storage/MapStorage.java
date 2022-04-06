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
    protected boolean isExist(String uuid) {
        return (String) searchKey(uuid) != null;
    }

    @Override
    protected boolean isExist(Resume resume) {
        return (String) searchKey(resume.getUuid()) != null;
    }

    @Override
    protected void updateResume(Resume resume) {
        map.replace((String) resume.getUuid(), resume);
        System.out.printf("Resume %s updated\n", resume.getUuid());
    }

    @Override
    protected void saveResume(Resume r) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void deleteResume(String uuid) {
        map.remove(uuid);
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
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            if (uuid.equals(entry.getValue().getUuid())) {
                return entry.getValue().getUuid();
            }
        }
        return null;
    }
}
