package basejava.webapp.storage;

import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.Resume;
import basejava.webapp.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private final SqlHelper sqlHelper;


    private  String clear = "DELETE  FROM resume";
    private String get = "SELECT * FROM resume r WHERE r.uuid = ?";
    private  String save = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
    private String  update = "UPDATE  resume  SET  full_name = ?";
    private String delete = "DELETE FROM resume WHERE uuid = ?";
    private String getAll = "SELECT * FROM resume ";
    private String counts = "SELECT count(*) FROM resume";


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl,dbUser,dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("CLEAR");
        sqlHelper.sqlException(clear, ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("GET");
       return sqlHelper.sqlException(get, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("SAVE");
        sqlHelper.sqlException(save, ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("UPDATE");
        sqlHelper.sqlException(update, ps -> {
            ps.setString(1, r.getFullName());
            isNotExist(ps, r.getUuid());
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("DELETE");
        sqlHelper.sqlException(delete, ps -> {
            ps.setString(1, uuid);
            isNotExist(ps, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GETALLSORTED");
        List<Resume> list = new ArrayList<>();
       return sqlHelper.sqlException(getAll, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                String fullName = rs.getString("full_name");
                list.add(new Resume(uuid, fullName));
            }
            list.sort(Comparator.comparing(Resume::getUuid));
            return list;
        });
    }

    @Override
    public int size() {
        LOG.info("SIZE");
        final int[] count = {0};
       return sqlHelper.sqlException(counts, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count[0] = rs.getInt(1);
            }
            return count[0];
        });

    }

    public void isNotExist(PreparedStatement ps, String uuid) throws SQLException{
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}
