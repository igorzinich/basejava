package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public class ArrayStorageTest extends AbstractStorageTest {
    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid_" + i));
            }
        } catch (StorageException e){
            Assert.fail("Переполнение произошло раньше времени");
        }
        storage.save(new Resume("uuid_10001"));
    }

}