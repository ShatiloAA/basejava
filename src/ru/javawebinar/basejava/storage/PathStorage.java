package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    private Serializer serializer;

    protected abstract void makeWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume makeRead(InputStream is) throws IOException;

    protected PathStorage(String dir, Serializer serializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or ist not writable");
        }
        this.serializer = serializer;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void makeSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
        makeUpdate(resume, path);
    }


    @Override
    protected void makeUpdate(Resume resume, Path path) {
        try {
            serializer.makeWrite(resume, new BufferedOutputStream(new FileOutputStream(path.toAbsolutePath().toString())));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void makeDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error", path.getFileName().toString());
        }
    }

    @Override
    protected Resume makeGet(Path path) {
        try {
            return serializer.makeRead(new BufferedInputStream(new FileInputStream(path.toAbsolutePath().toString())));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> listOfResumes() {
        List<Resume> resumes;
        try (Stream<Path> pathStream = Files.walk(directory)) {
            resumes = pathStream.filter(Files::isRegularFile).map(this::makeGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return resumes;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString() + uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }


    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::makeDelete);
        } catch (IOException e) {
            throw new StorageException("path delete error", null);
        }
    }

    @Override
    public int size() {
        int size = 0;
        try {
            size = (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }

        return size;
    }
}
