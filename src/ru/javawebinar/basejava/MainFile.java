package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        /*File filePath = new File(".\\.gitignore");
        try {
            System.out.println(filePath.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        File directory = new File("./src/ru/javawebinar/basejava");
        printDirectoryDeeply(directory);
    }

    private static void printDirectoryDeeply(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("    Имя файла: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println("Папка: " + file.getName());
                    printDirectoryDeeply(file);
                }
            }
        }
    }
}
