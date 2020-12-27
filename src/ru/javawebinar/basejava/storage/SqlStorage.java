package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (value, resume_uuid, type) VALUES(?,?,?)")) {
                insertContacts(r, ps, 1);
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "   WITH t1 AS (UPDATE resume SET full_name = ? WHERE uuid = ?) " +
                    "   UPDATE contact SET value = ? WHERE resume_uuid = ? and type = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                insertContacts(r, ps, 3);
                if (Arrays.stream(ps.executeBatch()).sum() <= 0) {
                   throw new NotExistStorageException(r.getUuid());
               }
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
                        "   WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet resultSet = ps.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        resume.addContact(ContactType.valueOf(resultSet.getString("type")),
                                resultSet.getString("value"));
                    } while (resultSet.next());
                    return resume;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r" +
                        "   LEFT JOIN contact c" +
                        "   ON r.uuid = c.resume_uuid" +
                        "   ORDER BY full_name,uuid",
                ps -> {
                    ListStorage listStorage = new ListStorage();
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        String uuid = resultSet.getString("uuid");
                        Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                        if (listStorage.getSearchKey(uuid) == null) {
                            resume.addContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                            listStorage.save(resume);
                        } else {
                            listStorage.get(uuid).addContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                        }
                    }
                    return listStorage.returnResumeList();
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void insertContacts(Resume r, PreparedStatement ps, int startParameterIndex) throws SQLException {
        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
            int index = startParameterIndex;
            ps.setString(startParameterIndex, e.getValue());
            ps.setString(++index, r.getUuid());
            ps.setString(++index, e.getKey().name());
            ps.addBatch();
        }
    }
}
