package basejava.webapp.storage.strategy;

import basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerialization implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();

            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeCollection(dos, r.getSections().entrySet(), entry -> {
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
                        writeCollection(dos, ((ListSection) section).getStringList(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((OrganizationSection) section).getOrganizations(), organization -> {
                            dos.writeUTF(organization.getContent());
                            dos.writeUTF(organization.getWebSite());
                            writeCollection(dos, organization.getPeriods(), period -> {
                                writeDate(dos, period.getStartTime());
                                writeDate(dos, period.getEndTime());
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readCollection(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSections(dis, sectionType));
            });

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
                return new ListSection(readList(dis, () -> dis.readUTF()));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readList(dis, () -> new Organization(
                        dis.readUTF(), dis.readUTF(), readList(dis, () -> new Period(
                        readDate(dis), readDate(dis), dis.readUTF(), dis.readUTF()))
                )));
            default:
                return null;
        }
    }

    private interface addCollecton {
        void add() throws IOException;
    }

    private interface writeElement<V> {
        void write(V v) throws IOException;
    }

    private interface readerElement<V> {
        V read() throws IOException;
    }

    private void readCollection(DataInputStream dis, addCollecton addCollecton) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            addCollecton.add();
        }
    }

    private <V> void writeCollection(DataOutputStream dos, Collection<V> collection, writeElement<V> writer) throws IOException {
        dos.writeInt(collection.size());
        for (V collect : collection
        ) {
            writer.write(collect);
        }
    }

    private <V> List<V> readList(DataInputStream dis, readerElement<V> reader) throws IOException {
        int size = dis.readInt();
        List<V> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
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

}
