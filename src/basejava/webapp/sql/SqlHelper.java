package basejava.webapp.sql;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

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
          throw  ExceptionUtil.bugCather(e);
        }
    }
    public <T> T transactionExecute (SqlTransaction executer){
        try (Connection connection = connectionFactory.getConnection()){
            try{
                connection.setAutoCommit(false);
                T resault = (T) executer.execute(connection);
                connection.commit();
                return resault;
            }catch (SQLException e){
                connection.rollback();
              throw  ExceptionUtil.bugCather(e);
            }
        }catch (SQLException e){
            throw new StorageException(e);
        }
    }

    public interface BlockCode <T>{
        T execute(PreparedStatement ps) throws SQLException;
    }
}
