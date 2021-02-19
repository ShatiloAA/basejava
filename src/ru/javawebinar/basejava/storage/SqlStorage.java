package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume VALUES(?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(r, connection);
            insertSections(r, connection);
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                String uuid = r.getUuid();
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
                delContacts(uuid, connection);
                insertContacts(r, connection);
                delSections(uuid, connection);
                insertSections(r, connection);
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r" +
                        "   LEFT JOIN contact c" +
                        "   ON r.uuid = c.resume_uuid" +
                        "   LEFT JOIN section s" +
                        "   ON r.uuid = s.resume_uuid" +
                        "   WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet resultSet = ps.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        addContacts(resultSet, resume);
                        addSections(resultSet, resume);
                    } while (resultSet.next());
                    return resume;
                });
    }


    @Override
 /*   public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r\n" +
                        "   LEFT JOIN contact c" +
                        "   ON r.uuid = c.resume_uuid" +
                        "   ORDER BY full_name,uuid",
                ps -> {
                    Map<String, Resume> resumesMap = new LinkedHashMap<>();
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        String uuid = resultSet.getString("uuid");
                        Resume resume = resumesMap.get(uuid);
                        if (resume == null) {
                            resume = new Resume(uuid, resultSet.getString("full_name"));
                            resumesMap.put(uuid, resume);
                        }
                        addContacts(resultSet, resume);
                    }
                    return new ArrayList<>(resumesMap.values());
                });
    }*/

    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        sqlHelper.execute("SELECT * FROM resume ORDER BY full_name,uuid",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
                    }
                    return null;
                });
        sqlHelper.execute("SELECT * FROM contact ORDER BY resume_uuid",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        for (Resume r : resumes) {
                            if (r.getUuid().equals(resultSet.getString("resume_uuid"))) {
                                addContacts(resultSet, r);
                            }
                        }
                    }

                    return null;
                });
        sqlHelper.execute("SELECT * FROM section ORDER BY resume_uuid",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        for (Resume r : resumes) {
                            if (r.getUuid().equals(resultSet.getString("resume_uuid"))) {
                                addSections(resultSet, r);
                            }
                        }
                    }

                    return null;
                });
        return resumes;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void insertContacts(Resume r, Connection connection) throws SQLException {
        if (r.getContacts().size() > 0) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, contact_type, contact_value) VALUES(?,?,?)")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void addContacts(ResultSet resultSet, Resume resume) throws SQLException {
        String value = resultSet.getString("contact_value");
        if (value != null) {
            resume.setContact(ContactType.valueOf(resultSet.getString("contact_type")), value);
        }
    }

    private void delContacts(String uuid, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void insertSections(Resume r, Connection connection) throws SQLException {
        if (r.getSections().size() > 0) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_value) VALUES(?,?,?)")) {
                for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, e.getKey().name());
                    Section section = e.getValue();
                    //ps.setString(3, checkAndConcatenatingList(section));
                    ps.setString(3, JsonParser.write(section, Section.class));
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private String checkAndConcatenatingList(Section section) {
        if (section instanceof ListSection) {
            return String.join("\n", ((ListSection) section).getInfoList());
        }
        return String.valueOf(section);
    }

    private void addSections(ResultSet resultSet, Resume resume) throws SQLException {
        String sectionType = resultSet.getString("section_type");
        if (sectionType != null) {
            SectionType type = SectionType.valueOf(sectionType);
            String value = resultSet.getString("section_value");
            resume.setSection(type, JsonParser.read(value, Section.class));
          /*  if (value.contains("\n")) {
                resume.setSection(type, new ListSection(value.split("\n")));
            } else {
                resume.setSection(type, new TextSection(value));
            }*/

        }
    }

    private void delSections(String uuid, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }
}
