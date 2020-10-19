package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class MainFile {
    public static void main(String[] args) {
        File directory = new File("./src/");
        //walk(directory,"");
        Path dir = Paths.get(".\\src\\ru\\javawebinar\\basejava\\exception");
        try {
            List<Path> paths = Files.walk(dir).filter(path -> Files.isRegularFile(path)).collect(Collectors.toList());
            paths.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //walk2(directory,0);

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

    static void walk(File directory, String indent) {
        File[] dirList = directory.listFiles();
        if (dirList == null) {
            return;
        }
        for (File file : dirList) {
            if (file.isDirectory()) {
                System.out.println(indent + "Directory: " + file.getName());
                walk(file, indent + " ");
            } else {
                System.out.println(indent + "File: " + file.getName());
            }
        }
    }
/*
    static void walk2(File directory, int count) {
        File[] dirList = directory.listFiles();
        if (dirList == null) {
            return;
        }
        for (File file : dirList) {
            if (file.isDirectory()) {
                System.out.println(indents(count) + "Directory: " + file.getName());
                walk(file, indents(++count));
            } else {
                System.out.println(indents(count)  + "File: " + file.getName());
            }
        }
    }

    static String indents(int count) {
        String spaces = "";
        for (int i = 0; i < count; i++) {
            spaces = spaces + " ";
        }
        return spaces;
    }*/
}
