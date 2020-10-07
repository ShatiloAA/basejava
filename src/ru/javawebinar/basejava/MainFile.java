package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        walk("./src/ru/javawebinar/basejava");

        /*String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
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

    }

    static void walk(String path) {
        File directory = new File(path);
        File[] dirList = directory.listFiles();
        if (dirList == null) {
            return;
        }

        for (File file : dirList) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                walk(file.getAbsolutePath());
            } else {
                System.out.println("File: " + file.getName());
            }

        }
    }
}
