package basejava.webapp.sql;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    public ExceptionUtil() {
    }
    public static StorageException bugCather(SQLException e){
        if (e instanceof PSQLException){
            if (e.getSQLState().equals("23505")){
               return  new ExistStorageException();
            }
        }
        return new StorageException(e);
    }
}
