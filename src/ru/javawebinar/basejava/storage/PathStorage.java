package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.Serializer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    private Serializer serializer;

    protected PathStorage(String dir, Serializer serializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or ist not writable");
        }
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
            serializer.makeWrite(resume, Files.newOutputStream(path));
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
            return serializer.makeRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> returnResumeList() {
        return checkIOAndReturnPaths(directory)
                .filter(Files::isRegularFile)
                .map(this::makeGet)
                .collect(Collectors.toList());
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }


    @Override
    public void clear() {
        checkIOAndReturnPaths(directory).forEach(this::makeDelete);
    }

    @Override
    public int size() {
        return (int) checkIOAndReturnPaths(directory).count();
    }

    private Stream<Path> checkIOAndReturnPaths(Path path) {
        try {
            return Files.list(path);
        } catch (IOException e) {
            throw new StorageException("path delete error", null);
        }
    }
}
