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
    protected boolean isExist(Object searchKey) {
        return (String) searchKey != null;
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        map.replace((String) searchKey, resume);
        System.out.printf("Resume %s updated\n", resume.getUuid());
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        map.put(r.getUuid(), r);
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
