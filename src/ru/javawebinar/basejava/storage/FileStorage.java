package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {

    private File directory;

    private Serializer serializer;

    protected FileStorage(File directory, Serializer serializer) {
        Objects.requireNonNull(directory);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory!");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable!");
        }
        this.directory = directory;
        this.serializer = serializer;
    }

    @Override
    protected void makeSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        makeUpdate(resume, file);
    }

    @Override
    protected void makeUpdate(Resume resume, File file) {
        try {
            serializer.makeWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void makeDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected Resume makeGet(File file) {
        try {
            return serializer.makeRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> returnResumeList() {
        return Arrays.stream(checkNullAndReturnFiles(directory))
                .map(this::makeGet)
                .collect(Collectors.toList());
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        File[] files = checkNullAndReturnFiles(directory);
            for (File file : files) {
                if (!file.isDirectory())
                    makeDelete(file);
            }
    }

    @Override
    public int size() {
        return checkNullAndReturnFiles(directory).length;
    }

    private File[] checkNullAndReturnFiles(File file) {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error");
        }
        return files;
    }
}
