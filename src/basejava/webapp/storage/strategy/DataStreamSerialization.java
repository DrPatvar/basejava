package basejava.webapp.storage.strategy;

import basejava.webapp.model.*;

import java.io.*;
import java.util.Map;

public class DataStreamSerialization implements StreamSerializer {


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
                Section section = entry.getValue();
               switch (sectionType){
                   case PERSONAL :
                   case OBJECTIVE :
                       dos.writeUTF(((TextSection)section).getContent());
                   break;
                   case ACHIEVEMENT:
                   case QUALIFICATIONS:
                       dos.writeUTF(String.valueOf(((ListSection)section).getStringList()));
                   break;
                   case EXPERIENCE:
                   case EDUCATION:
                       dos.writeUTF(((OrganizationSection)section).getOrganizations());
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
            for (int i = size; i <count ; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType,readSections(dis,sectionType));
            }
            return resume;
        }
    }

    private Section readSections(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType){
            case PERSONAL :
            case OBJECTIVE:
                return new TextSection(dis.readUTF()) ;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(dis.readUTF());
            case EXPERIENCE:
            case EDUCATION:

        }
       return null;
    }
}
