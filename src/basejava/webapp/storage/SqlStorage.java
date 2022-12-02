package basejava.webapp.storage;

import basejava.webapp.exception.ExistStorageException;
import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.exception.StorageException;
import basejava.webapp.model.Resume;
import basejava.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("CLEAR");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("GET");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
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
    public void update(Resume r) {
        LOG.info("UPDATE");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE  resume SET full_name = ?")) {
             ps.setString(2, r.getFullName());
             ps.executeUpdate();
             ResultSet rs = ps.executeQuery();
             if(rs.next()){
                 throw new NotExistStorageException(r.getUuid());
             }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        LOG.info("SAVE");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                throw new ExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        LOG.info("DELETE");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ps.executeUpdate();
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
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
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ")) {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Нет элементов");
            }
            String uuid = rs.getString("uuid");
            String fullName = rs.getString("full_name");
             list.add(new Resume(uuid, fullName));
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
             PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume")) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            count = rs.getInt("count(*)");
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return count;
    }
}
