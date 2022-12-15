package basejava.webapp.storage;

import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.ContactType;
import basejava.webapp.model.Resume;
import basejava.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("CLEAR");
        sqlHelper.execute("DELETE  FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("GET");
        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                if (value!=null){
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    resume.addContact(type, value);
                }
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("SAVE");
        sqlHelper.transactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            addContact(connection,r);
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("UPDATE");
        sqlHelper.transactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE  resume r  SET  full_name = ? WHERE r.uuid = ? ")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    save(r);
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContact(connection,r);
            addContact(connection,r);
            return null;
        });
    }
       private  void addContact(Connection conn, Resume resume) throws SQLException {
         PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)");
           for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
               String value = e.getValue();
               if (value != null) {
                   ps.setString(1, resume.getUuid());
                   ps.setString(2, e.getKey().name());
                   ps.setString(3, value);
                   ps.addBatch();
               }
               ps.executeBatch();
           }
       }

       private void deleteContact(Connection connection, Resume resume)throws SQLException{
        PreparedStatement ps = connection.prepareStatement("DELETE  FROM contact WHERE resume_uuid = ?");
        ps.setString(1, resume.getUuid());
        ps.executeUpdate();
       }

    @Override
    public void delete(String uuid) {
        LOG.info("DELETE");
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GETALLSORTED");
        List<Resume> list = new ArrayList<>();
       sqlHelper.transactionExecute(connection -> {
           try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume")) {
               ResultSet rs = ps.executeQuery();
               while (rs.next()){
                   list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
               }
           }
           try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact")) {
               ResultSet rs = ps.executeQuery();
               while (rs.next()){
                   String uuid = rs.getString("resume_uuid");
                   String value = rs.getString("value");
                   ContactType type = ContactType.valueOf(rs.getString("type"));
                   for (Resume r:list
                   ) {
                       if (r.getUuid().equals(uuid)){
                           r.addContact(type, value);
                       }
                   }
               }
           }
           list.sort(Comparator.comparing(Resume::getUuid).thenComparing(Resume::getFullName));
           return list;
       } );
        return list;
    }

    @Override
    public int size() {
        LOG.info("SIZE");
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
