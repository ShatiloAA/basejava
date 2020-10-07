package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected abstract void makeWrite(Resume resume, File file) throws IOException;

    protected abstract Resume makeRead(File file) throws IOException;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory!");
        }
        if (!directory.canRead() || directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable!");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> listOfResumes() {
        List<Resume> resumeList = new ArrayList<>();
        if (size() > 0) {
            File[] files = directory.listFiles();
            for (File file : files) {
                try {
                    Resume resume = makeRead(file);
                    resumeList.add(resume);
                } catch (IOException e) {
                    throw new StorageException("IO error", file.getName(), e);
                }
            }
        }
        return resumeList;
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
    protected void makeSave(Resume resume, File file) {
        try {
            file.createNewFile();
            makeWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void makeUpdate(Resume resume, File file) {
        try {
            makeWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }

    }

    @Override
    protected void makeDelete(String uuid, File file) {
        file.delete();
    }

    @Override
    protected Resume makeGet(File file) {
        try {
            return makeRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (size() > 0) {
            for (File file : files) {
                if (!file.isDirectory())
                    file.delete();
            }
        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }
}
