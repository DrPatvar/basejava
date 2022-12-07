package basejava.webapp.sql;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(String sql, BlockCode blockCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            T resault = (T) blockCode.execute(ps);
            return resault;
        } catch (SQLException e) {
            if (e.getErrorCode() == 0) {
                throw new ExistStorageException();
            }
            throw new StorageException(e);
        }
    }

    public interface BlockCode <T>{
        T execute(PreparedStatement ps) throws SQLException;
    }
}
