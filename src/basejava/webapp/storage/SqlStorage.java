package basejava.webapp.storage;

import basejava.webapp.exception.NotExistStorageException;
import basejava.webapp.model.*;
import basejava.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
          return   sqlHelper.transactionExecute(connection -> {
                try(PreparedStatement ps = connection.prepareStatement
                        ("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid = ?")) {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                     Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, resume);
                    } while (rs.next());
                    try(PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")){
                     ps2.setString(1, uuid);
                     ResultSet rs2 = ps2.executeQuery();
                     while (rs2.next()){
                         addSection(rs2,resume);
                     }
                    }
                    return resume;
                }
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
            insertContact(connection, r);
            insertSection(connection,r);
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
            deleteContact(connection, r);
            insertContact(connection, r);
            deleteSection(connection, r);
            insertSection(connection, r);
            return null;
        });
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
        LOG.info("GET_ALL_SORTED");
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
               while (rs.next()) {
                   String uuid = rs.getString("resume_uuid");
                   for (Resume resume : list
                   ) {
                       if (resume.getUuid().equals(uuid)) {
                           addContact(rs, resume);
                       }
                   }
               }
           }
           try(PreparedStatement ps = connection.prepareStatement("SELECT  * FROM  section")){
               ResultSet rs = ps.executeQuery();
               while (rs.next()){
                   String uuid = rs.getString("resume_uuid");
                   for (Resume resume : list
                   ) {
                       if (resume.getUuid().equals(uuid)) {
                           addSection(rs, resume);
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

    private void insertContact(Connection conn, Resume resume) throws SQLException {
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

    private void deleteContact(Connection connection, Resume resume) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE  FROM contact WHERE resume_uuid = ?");
        ps.setString(1, resume.getUuid());
        ps.execute();
    }

    private void insertSection(Connection connection, Resume resume) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)");
        for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
            SectionType sectionType = e.getKey();
            Section section = e.getValue();
            if (section != null) {
                switch (sectionType){
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, sectionType.name());
                        ps.setString(3, ((TextSection)section).getContent() );
                        ps.addBatch();
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        StringBuilder text = new StringBuilder();
                        for (String s: ((ListSection)section).getStringList()
                             ) {
                            text.append(s).append("\n");
                        }
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, sectionType.name());
                        ps.setString(3, text.toString());
                        ps.addBatch();
                        break;
                }
            }
            ps.executeBatch();
        }
    }

    private void deleteSection(Connection connection, Resume resume) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("DELETE FROM section WHERE resume_uuid =?");
        ps.setString(1, resume.getUuid());
        ps.execute();
    }
    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value!=null){
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }
    private void  addSection(ResultSet rs, Resume resume) throws SQLException{
        String value = rs.getString("value");
        String type = rs.getString("type");
        if (value!=null){
            switch (type){
                case "PERSONAL":
                case "OBJECTIVE":
                    resume.addSection(SectionType.valueOf(type), new TextSection(value));
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    List list = new ArrayList(Arrays.asList(value.split("\n")));
                    resume.addSection(SectionType.valueOf(type), new ListSection(list));
                    break;
            }
        }
    }
}
