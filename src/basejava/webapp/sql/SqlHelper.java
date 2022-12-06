package basejava.webapp.sql;

import basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private  Connection conn = null;
    private PreparedStatement ps = null;
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public Connection getConnection () throws SQLException {
       conn = connectionFactory.getConnection();
            return conn;
    }

    public PreparedStatement getPs(Connection connection, String sql)throws SQLException {
         ps = getConnection().prepareStatement(sql);
        return ps;
    }
    public void sqlException (Connection conn, String sql ){
      try(PreparedStatement preparedStatement=  getPs(getConnection(),
              sql)){

      }catch (SQLException e){
          throw new StorageException(e);
      }
    }


}
