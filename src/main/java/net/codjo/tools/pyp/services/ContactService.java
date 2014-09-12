package net.codjo.tools.pyp.services;

import net.codjo.tools.pyp.model.Contact;
import net.codjo.tools.pyp.xml.ContactXmlCodec;
import net.codjo.tools.pyp.xml.XmlCodec;
import net.codjo.util.file.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ContactService implements Serializable {
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock readLock = rwl.readLock();
    private final File configFile;


    ContactService(File configPath) {
        this.configFile = new File(configPath, "PypContacts.xml");
    }


    public ContactService(String configPath) {
        this(new File(configPath));
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    List<Contact> loadConfig() throws IOException {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            throw new IOException(String.format("Impossible de charger le fichier des contacts: '%s'",
                    configFile.getAbsolutePath()));
        }
        try {
            readLock.lock();
            String xmlConfig = FileUtil.loadContent(configFile.toURI().toURL(), XmlCodec.ENCODING);
            return new ContactXmlCodec().fromXml(xmlConfig);
        } finally {
            readLock.unlock();
        }
    }

    private List<Contact> getContacts() {
        try {
            List<Contact> resultList = new ArrayList<Contact>();
            List<Contact> brinList = loadConfig();
            for (Contact brin : brinList) {
                resultList.add(brin);
            }
            return resultList;
        } catch (Exception e) {
            return new ArrayList<Contact>();
        }
    }


    protected Set<String> extractMailList() {
        Set<String> userList = new TreeSet<String>();
        List<Contact> brinList = getContacts();
        for (Contact contact : brinList) {
            userList.add(contact.getMail());
        }
        return userList;
    }
}
