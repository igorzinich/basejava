package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistedSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
        System.out.printf("Resume %s updated\n", resume.getUuid());
    }

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistedSearchKey(r.getUuid());
        saveResume(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        deleteResume(searchKey);
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKeyObject = searchKey(uuid);
        if (!isExist(searchKeyObject)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKeyObject;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKeyObject = searchKey(uuid);
        if (isExist(searchKeyObject)) {
            throw new ExistStorageException(uuid);
        }
        return searchKeyObject;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = copyAllResume();
        list.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return list;
    }

    protected abstract List<Resume> copyAllResume();

    protected abstract boolean isExist(Object searchKey);

    protected abstract void updateResume(Resume resume, Object searchKey);

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Object searchKey(String uuid);
}
