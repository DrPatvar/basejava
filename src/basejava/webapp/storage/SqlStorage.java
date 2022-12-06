package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;
import basejava.webapp.sql.ConnectionFactory;
import basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper;

    private  String clear = "DELETE  FROM resume";
    private String get = "SELECT * FROM resume r WHERE r.uuid = ?";
    private  String save = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
    private String  update = "UPDATE  resume  SET  full_name = ?";
    private String delete = "DELETE FROM resume WHERE uuid = ?";
    private String getAll = "SELECT * FROM resume ";
    private String counts = "SELECT count(*) FROM resume";


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = new ConnectionFactory() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        };
        sqlHelper = new SqlHelper(dbUrl,dbUser,dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("CLEAR");
       /* try {
            sqlHelper.getPs(sqlHelper.getConnection(), clear).execute();
        } catch (SQLException e) {
        }*/

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(clear)) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("GET");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(get)) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        LOG.info("SAVE");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(save)) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
        } catch (SQLException e) {
             if (e.getErrorCode() == 0){
                 throw new ExistStorageException(r.getUuid());
             }
                throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        LOG.info("UPDATE");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(update)) {
            ps.setString(1, r.getFullName());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        LOG.info("DELETE");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(delete)) {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GETALLSORTED");
        List<Resume> list = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(getAll)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String uuid = rs.getString("uuid").trim();
                String fullName = rs.getString("full_name");
                list.add(new Resume(uuid, fullName));
            }
            list.sort(Comparator.comparing(Resume::getUuid));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return list;
    }

    @Override
    public int size() {
        LOG.info("SIZE");
        int count = 0;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(counts)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return count;
    }
}
