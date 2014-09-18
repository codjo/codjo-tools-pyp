package net.codjo.tools.pyp.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.codjo.tools.pyp.model.Contact;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class ContactXmlCodec {
    public static final String ENCODING = "UTF-8";
    private final XStream xStream;


    public ContactXmlCodec() {
        xStream = new XStream(new DomDriver());
        xStream.alias("contactList", ContactRepository.class);
        xStream.alias("contact", Contact.class);
        xStream.setMode(XStream.NO_REFERENCES);
    }


    public String toXml(List<Contact> contactList) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(outputStream, ENCODING);
            writer.write("<?xml version=\"1.0\" encoding=\"" + ENCODING + "\" ?>");
            xStream.toXML(new ContactRepository(contactList), writer);
            return outputStream.toString(ENCODING);
        } finally {
            if (writer != null) {
                writer.close();
            }
            outputStream.close();
        }
    }

    @SuppressWarnings({"unchecked"})
    public List<Contact> fromXml(String xmlcontent) {
        return ((ContactRepository) xStream.fromXML(xmlcontent)).getContacts();
    }


    private static class ContactRepository {
        private List<Contact> contacts;


        private ContactRepository(List<Contact> repository) {
            this.contacts = repository;
        }


        public List<Contact> getContacts() {
            return contacts;
        }


        public void setContacts(List<Contact> repository) {
            this.contacts = repository;
        }
    }
}
