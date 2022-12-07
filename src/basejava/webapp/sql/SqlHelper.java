package basejava.webapp.sql;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public SqlHelper() {
    }

    public <T> T sqlExecute(ConnectionFactory connectionFactory, String sql, BlockCode blockCode) {
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
