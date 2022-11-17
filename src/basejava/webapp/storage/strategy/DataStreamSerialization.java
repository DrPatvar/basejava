package basejava.webapp.storage.strategy;

import basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerialization implements StreamSerializer {

    private <V> void writerList(DataOutputStream dos, List<V> lists) throws IOException {
        dos.writeInt(lists.size());
        for (V list : lists
        ) {
            dos.writeUTF(String.valueOf(list));
        }
    }

    private <V> List<V> readList(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<V> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((V) dis.readUTF());
        }
        return list;
    }

    private void writeDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonthValue());
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private void writePeriod(DataOutputStream dos, List<Period> periods) throws IOException {
        dos.writeInt(periods.size());
        for (Period period : periods
        ) {
            writeDate(dos, period.getStartTime());
            writeDate(dos, period.getEndTime());
            dos.writeUTF(period.getTitle());
            dos.writeUTF(period.getDescription());
        }
    }

    private void writeOrg(DataOutputStream dos, List<Organization> organizations) throws IOException {
        dos.writeInt(organizations.size());
        for (Organization org : organizations) {
            dos.writeUTF(org.getContent());
            dos.writeUTF(org.getWebSite());
            writePeriod(dos, org.getPeriods());
        }
    }

    private List<Organization> readOrg(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Organization> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(new Organization(dis.readUTF(), dis.readUTF(), readPeriod(dis)));
        }
        return list;
    }

    private List<Period> readPeriod(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Period> periods = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            periods.add(new Period(readDate(dis), readDate(dis), dis.readUTF(), dis.readUTF()));
        }
        return periods;
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()
            ) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()
            ) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                Section section = entry.getValue();
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writerList(dos, ((ListSection) section).getStringList());
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> listOrg = (((OrganizationSection) section).getOrganizations());
                        writeOrg(dos, listOrg);
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int count = dis.readInt();
            for (int i = 0; i <count ; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType,readSections(dis,sectionType));
            }
            return resume;
        }
    }

    private Section readSections(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readOrg(dis));
            default:
                return null;
        }
    }
}
