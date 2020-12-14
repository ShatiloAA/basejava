package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    public <T> T execute(String sqlCommand, SqlExecutor<T> sqlExecutor) {

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement ps = connection.prepareStatement(sqlCommand);
        ) {
            return sqlExecutor.execute(ps);
        } catch (SQLException e) {
            if (e instanceof PSQLException && e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }

    public interface SqlExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
