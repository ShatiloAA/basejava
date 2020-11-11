package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DataStreamSerializer implements Serializer {

    //https://www.baeldung.com/java-lambda-exceptions
    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }

    //wapper for Lambda throwing exceptions
    private static <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, Exception> throwingConsumer) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception e) {
                throw new StorageException("IO exception", null, e);
            }
        };
    }

    @Override
    public void makeWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            for (Map.Entry<SectionType, Section> sectionEntry : resume.getSections().entrySet()) {
                SectionType sectionType = sectionEntry.getKey();
                dos.writeUTF(sectionType.name());
                if (sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)) {
                    dos.writeUTF(sectionEntry.getValue().toString());
                } else if (sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)) {
                    writeListSection(dos, ((ListSection) sectionEntry.getValue()).getInfoList());
                } else if (sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)) {
                    writeOrgSection(dos, ((OrganizationSection) sectionEntry.getValue()).getOrganizations());
                }
            }

        }
    }

    @Override
    public Resume makeRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            while (dis.available() > 0) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                if (type.equals(SectionType.PERSONAL) || type.equals(SectionType.OBJECTIVE)) {
                    resume.addSection(type, new TextSection(dis.readUTF()));
                } else if (type.equals(SectionType.ACHIEVEMENT) || type.equals(SectionType.QUALIFICATIONS)) {
                    resume.addSection(type, new ListSection(readListSection(dis)));
                } else if (type.equals(SectionType.EXPERIENCE) || type.equals(SectionType.EDUCATION)) {
                    resume.addSection(type, readOrgSection(dis));
                }
            }
            return resume;
        }
    }

    private String writeNull(String writeString) {
        if (writeString == null) {
            return "null";
        }
        return writeString;
    }

    private String readNull(String readString) {
        if (readString.equals("null")) {
            return null;
        }
        return readString;
    }

    private void writeListSection(DataOutputStream dataOutputStream, List<String> listOfSections) throws IOException {
        dataOutputStream.writeInt(listOfSections.size());
        for (String s : listOfSections) {
            dataOutputStream.writeUTF(s);
        }
    }

    private List<String> readListSection(DataInputStream dataInputStream) throws IOException {
        List<String> listItems = new ArrayList<>();
        int sizeItems = dataInputStream.readInt();
        for (int i = 0; i < sizeItems; i++) {
            listItems.add(dataInputStream.readUTF());
        }
        return listItems;
    }

    private void writeOrgSection(DataOutputStream dataOutputStream, List<Organization> listOfOrgs) throws IOException {
        dataOutputStream.writeInt(listOfOrgs.size());

        listOfOrgs.forEach(throwingConsumerWrapper(org -> {
                    dataOutputStream.writeUTF(org.getHomepage().getName());
                    dataOutputStream.writeUTF(writeNull(org.getHomepage().getUrl()));
                    dataOutputStream.writeInt(org.getPositions().size());

                    org.getPositions().forEach(throwingConsumerWrapper(position -> {
                                dataOutputStream.writeUTF(position.getTitle());
                                dataOutputStream.writeInt(position.getStartDate().getYear());
                                dataOutputStream.writeInt(position.getStartDate().getMonth().getValue());
                                dataOutputStream.writeInt(position.getEndDate().getYear());
                                dataOutputStream.writeInt(position.getEndDate().getMonth().getValue());
                                dataOutputStream.writeUTF(writeNull(position.getDescription()));
                            })
                    );
                })
        );
    }

    private OrganizationSection readOrgSection(DataInputStream dataInputStream) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int organizationsSize = dataInputStream.readInt();

        for (int i = 0; i < organizationsSize; i++) {
            String homepage = dataInputStream.readUTF();
            String url = dataInputStream.readUTF();
            Link link = new Link(homepage, readNull(url));
            int sizeOfPositions = dataInputStream.readInt();
            List<Organization.Position> positions = new ArrayList<>();
            for (int j = 0; j < sizeOfPositions; j++) {
                positions.add(new Organization.Position(
                        dataInputStream.readUTF(),
                        LocalDate.of(
                                dataInputStream.readInt(),
                                dataInputStream.readInt(),
                                1),
                        LocalDate.of(
                                dataInputStream.readInt(),
                                dataInputStream.readInt(),
                                1),
                        readNull(dataInputStream.readUTF())));
            }
            organizations.add(new Organization(link, positions));
        }
        return new OrganizationSection(organizations);
    }



}