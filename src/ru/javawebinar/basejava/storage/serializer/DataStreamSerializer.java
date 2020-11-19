package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DataStreamSerializer implements Serializer {

    @Override
    public void makeWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeWithException(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(resume.getSections().entrySet(), dos, entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(entry.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(((ListSection) entry.getValue()).getInfoList(), dos, dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeWithException(((OrganizationSection) entry.getValue()).getOrganizations(), dos,
                                org -> {
                                    dos.writeUTF(org.getHomepage().getName());
                                    dos.writeUTF(writeNull(org.getHomepage().getUrl()));
                                    writeWithException(org.getPositions(), dos,
                                            position -> {
                                                dos.writeUTF(position.getTitle());
                                                writeLocalDate(position.getStartDate(), dos);
                                                writeLocalDate(position.getEndDate(), dos);
                                                dos.writeUTF(writeNull(position.getDescription()));
                                            });
                                });
                        break;
                }
            });
        }
    }

    @Override
    public Resume makeRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(type, new ListSection(readListSection(dis, dis::readUTF)));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        resume.addSection(type, readOrgSection(dis));
                        break;
                }
            });
            return resume;
        }
    }

    private String writeNull(String writeString) {
        return writeString == null ? "null" : writeString;
    }

    private String readNull(String readString) {
        return readString.equals("null") ? null : readString;
    }

    private <T> List<T> readListSection(DataInputStream dataInputStream, CollectionReader<T> collectionReader) throws IOException {
        List<T> listItems = new ArrayList<>();
        int sizeItems = dataInputStream.readInt();
        for (int i = 0; i < sizeItems; i++) {
            listItems.add(collectionReader.read());
        }
        return listItems;
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dataOutputStream, CollectionWriter<T> collectionWriter) throws IOException {
        Objects.requireNonNull(collection);
        dataOutputStream.writeInt(collection.size());
        for (T item : collection) {
            collectionWriter.accept(item);
        }
    }

    private void readWithException(DataInputStream dataInputStream, CollectionManipulator collectionManipulator) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            collectionManipulator.manipulate();
        }
    }

    private OrganizationSection readOrgSection(DataInputStream dataInputStream) throws IOException {
        return new OrganizationSection(readListSection(dataInputStream,
                () -> new Organization(
                        new Link(dataInputStream.readUTF(), readNull(dataInputStream.readUTF())),
                        readListSection(dataInputStream, () ->
                                new Organization.Position(
                                        dataInputStream.readUTF(),
                                        readLocalDate(dataInputStream),
                                        readLocalDate(dataInputStream),
                                        readNull(dataInputStream.readUTF())))
                ))
        );

    }

    private void writeLocalDate(LocalDate localDate, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(localDate.getYear());
        dataOutputStream.writeInt(localDate.getMonthValue());
    }

    private LocalDate readLocalDate(DataInputStream dataInputStream) throws IOException {
        return LocalDate.of(
                dataInputStream.readInt(),
                dataInputStream.readInt(),
                1
        );
    }

    @FunctionalInterface
    public interface CollectionWriter<T> {
        void accept(T t) throws IOException;
    }

    @FunctionalInterface
    public interface CollectionReader<T> {
        T read() throws IOException;
    }

    @FunctionalInterface
    public interface CollectionManipulator {
        void manipulate() throws IOException;
    }

}