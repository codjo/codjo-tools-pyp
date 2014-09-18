package net.codjo.tools.pyp.services;

import net.codjo.tools.pyp.model.Contact;
import net.codjo.util.file.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;

/**
 *
 */
public class ContactServiceTest {
    private ContactService service = new ContactService("");

    public static void initContactsFile(File targetFile) throws IOException {
        String contactsfileContent = "<contactList>\n" +
                "    <contacts>\n" +
                "    <contact><mail>contact1@gmail.com</mail></contact>\n" +
                "    <contact><mail>contact2@yahoo.com</mail></contact>\n" +
                "    <contact><mail>contact3@yahoo.com</mail></contact>\n" +
                "    </contacts>\n" +
                "</contactList>";
        FileUtil.saveContent(targetFile, contactsfileContent);
    }

    @Test
    public void test_getContacts() throws Exception {
        String configPath = getClass().getResource("/").getPath();
        service = new ContactService(configPath);
        Set<String> brins = service.extractMailList();

        assertTrue((brins.contains("contact1@gmail.com")));
        assertTrue((brins.contains("contact2@yahoo.com")));
        assertTrue((brins.contains("contact3@yahoo.com")));
    }

    @Test
    public void test_loadNoConfig() throws Exception {
        service = new ContactService("C:\\dummyFilePath");
        List<Contact> brins = null;
        try {
            service.loadConfig();
            fail("An IOException should have been raised");
        } catch (IOException e) {
            assertEquals("Impossible de charger le fichier des contacts: 'C:\\dummyFilePath\\PypContacts.xml'", e.getMessage());
        }
    }
}
