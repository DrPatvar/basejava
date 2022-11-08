package basejava.webapp.storage.strategy;

import basejava.webapp.model.*;
import basejava.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStreamSerializer(){
        xmlParser = new XmlParser(Resume.class, OrganizationSection.class, TextSection.class,
                ListSection.class, Period.class, Organization.class);
    }
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try(Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
