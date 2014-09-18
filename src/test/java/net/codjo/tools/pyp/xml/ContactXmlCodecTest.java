package net.codjo.tools.pyp.xml;

import net.codjo.test.common.XmlUtil;
import net.codjo.tools.pyp.model.Contact;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ContactXmlCodecTest {
    private ContactXmlCodec xmlCodec = new ContactXmlCodec();

    @Test
    public void test_toXml() throws Exception {
        List<Contact> contacts = new ArrayList<Contact>();

        Contact contact = new Contact("mail_1@gmail.com");
        contacts.add(contact);
        contact = new Contact("mail_2@gmail.com");
        contacts.add(contact);
        contact = new Contact("mail_3@gmail.com");
        contacts.add(contact);


        String actual = xmlCodec.toXml(contacts);

        XmlUtil.assertEquivalent("<contactList>"
                + "       <contacts>\n"
                + "          <contact><mail>mail_1@gmail.com</mail></contact>\n"
                + "          <contact><mail>mail_2@gmail.com</mail></contact>\n"
                + "          <contact><mail>mail_3@gmail.com</mail></contact>\n"
                + "       </contacts>\n"
                + "       </contactList>\n"
                , actual);
    }


    @Test
    public void test_fromXml() throws Exception {
        String expected = "<contactList>"
                + "       <contacts>\n"
                + "         <contact><mail>contact1@gmail.com</mail></contact>\n"
                + "         <contact><mail>contact2@yahoo.fr</mail></contact>\n"
                + "         <contact><mail></mail></contact>\n"
                + "       </contacts>\n"
                + "  </contactList>\n";

        String actual = xmlCodec.toXml(xmlCodec.fromXml(expected));
        XmlUtil.assertEquivalent(expected, actual);
    }

}
