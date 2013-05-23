package net.codjo.tools.pyp.services;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.codjo.tools.pyp.PypApplication;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import net.codjo.tools.pyp.model.filter.BrinFilter;
import net.codjo.tools.pyp.xml.XmlCodec;
import net.codjo.util.file.FileUtil;
import org.apache.wicket.Component;

public class BrinService {
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock readLock = rwl.readLock();
    private final Lock writeLock = rwl.writeLock();
    private final File configFile;


    BrinService(File configFile) {
        this.configFile = configFile;
    }


    public BrinService(String configPath) {
        this(new File(configPath));
    }


    public static BrinService getBrinService(Component wicketComponent) {
        return ((PypApplication)wicketComponent.getApplication()).getBrinService();
    }


    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    List<Brin> loadConfig() throws IOException {
        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdirs();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            throw new IOException(String.format("Impossible de charger le fichier des brins: '%s'",
                                                configFile.getAbsolutePath()));
        }
        try {
            readLock.lock();
            String xmlConfig = FileUtil.loadContent(configFile.toURI().toURL(), XmlCodec.ENCODING);
            List<Brin> brinList = new XmlCodec().fromXml(xmlConfig);
            Collections.sort(brinList, compareByCreationDate());
            return brinList;
        }
        finally {
            readLock.unlock();
        }
    }


    private void saveConfig(List<Brin> brinList) throws IOException {
        try {
            writeLock.lock();
            String xmlContent = new XmlCodec().toXml(brinList);
            FileUtil.saveContent(configFile, xmlContent, XmlCodec.ENCODING);
        }
        finally {
            writeLock.unlock();
        }
    }


    private static Comparator<Brin> compareByCreationDate() {
        return new Comparator<Brin>() {
            public int compare(Brin brinUn, Brin brinO2) {
                int result = brinUn.getStatus().getOrder() - brinO2.getStatus().getOrder();
                if (result == 0) {
                    result = -brinUn.getCreationDate().compareTo(brinO2.getCreationDate());
                }
                return result;
            }
        };
    }


    public List<Brin> getAllBrins() {
        return getBrins(null);
    }


    public List<Brin> getBrins(BrinFilter brinFilter) {
        try {
            List<Brin> resultList = new ArrayList<Brin>();
            List<Brin> brinList = loadConfig();
            if (brinFilter == null) {
                return brinList;
            }
            else {
                for (Brin brin : brinList) {
                    if (brinFilter.doFilter(brin)) {
                        resultList.add(brin);
                    }
                }
                return resultList;
            }
        }
        catch (Exception e) {
            return new ArrayList<Brin>();
        }
    }


    public Brin getBrin(String uuid) throws IOException {
        List<Brin> brinList = getAllBrins();
        for (Brin brin : brinList) {
            if (brin.getUuid().equals(uuid)) {
                return brin;
            }
        }
        return null;
    }


    public void addBrin(Brin brin) {
        List<Brin> brins = getAllBrins();
        brin.setUuid(generateUniqueId());
        brins.add(brin);
        try {
            saveConfig(brins);
        }
        catch (IOException e) {
            throw new IllegalStateException("Erreur en sauvegardant le brin : " + brin.getTitle() + "\ndans le fichier "
                                            + configFile.getAbsolutePath(), e);
        }
    }


    public void updateBrin(Brin brin) {
        writeLock.lock();
        try {
            List<Brin> brins = getAllBrins();
            List<Brin> newList = updateBrinInList(brin, brins);
            saveConfig(newList);
        }
        catch (IOException e) {
            throw new IllegalStateException(
                  "Erreur en mettant à jour le brin suivant : " + brin.getTitle() + "\ndans le fichier "
                  + configFile.getAbsolutePath(), e);
        }
        finally {
            writeLock.unlock();
        }
    }


    protected List<Brin> updateBrinInList(Brin brin, List<Brin> brins) {
        boolean trouve = false;
        int index = 0;
        for (Brin myBrin : brins) {
            if (myBrin.getUuid().equals(brin.getUuid())) {
                trouve = true;
                break;
            }
            index++;
        }

        if (trouve && index >= 0) {
            List<Brin> result = new ArrayList<Brin>();
            result.addAll(brins.subList(0, index));
            result.add(brin);
            result.addAll(brins.subList(index + 1, brins.size()));
            return result;
        }
        else {
            return brins;
        }
    }


    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }


    public Map<Status, Integer> calculateBrinNumber(List<Brin> brinList) {
        Map<Status, Integer> resultMap = new TreeMap<Status, Integer>();
        Status[] statusValues = Status.values();
        for (Status status : statusValues) {
            resultMap.put(status, 0);
        }

        if (brinList != null) {
            for (Brin brin : brinList) {
                Status status = brin.getStatus();
                Integer integer = resultMap.get(status);
                integer++;
                resultMap.put(status, integer);
            }
        }
        return resultMap;
    }
}
