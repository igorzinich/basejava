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
    protected void updateResume(Resume resume, int index) {
        map.replace(String.valueOf(index), resume);
        System.out.printf("Resume %s updated\n", resume.getUuid());
    }

    @Override
    protected void saveResume(Resume r, int index) {
        map.put(String.valueOf(index), r);
    }

    @Override
    protected Resume getResume(int index) {
        return map.get(String.valueOf(index));
    }

    @Override
    protected void deleteResume(int index) {
        map.remove(String.valueOf(index));
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[map.size()]);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected int findIndex(String uuid) {
        for (Map.Entry<String, Resume> entry : map.entrySet()){
            if (uuid.equals(entry.getValue().getUuid())) {
                return Integer.parseInt(entry.getKey());
            }
        }
        return -1;
    }
}
