package basejava.webapp.storage.strategy;

import basejava.webapp.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerialization implements StreamSerializer {

    private <K> void writerList(DataOutputStream dos, List<K> lists) throws IOException {
        dos.writeInt(lists.size());
        for (K list : lists
        ) {
            dos.writeUTF(String.valueOf(list));
        }
    }

    private <K> List<K> readList(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<K> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((K) dis.readUTF());
        }
       return list;
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
            } // запись в файл контактов
            //начало записи секции
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size()); // подсчет кол-ва элементов
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()
            ) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name()); // запись ключа
                Section section = entry.getValue(); //нахождение данных резюме секций, контента
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
          //чтение  в Map<SectionType, Section>
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
            default:
                return null;
        }
    }
}
