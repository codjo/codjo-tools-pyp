package net.codjo.tools.pyp.xml;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.List;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Team;
/**
 *
 */
public class XmlCodec {
    private final XStream xStream;


    public XmlCodec() {
        xStream = new XStream(new DomDriver());
        xStream.alias("brinList", BrinRepository.class);
        xStream.alias("brin", Brin.class);
        xStream.alias("team", Team.class);
        xStream.setMode(XStream.NO_REFERENCES);
    }


    public String toXml(List<Brin> brinList) {
        return xStream.toXML(new BrinRepository(brinList));
    }


    public List<Brin> fromXml(String xmlConfigxmlBrinList) {
        return ((BrinRepository)xStream.fromXML(xmlConfigxmlBrinList)).getRepository();
    }


    private static class BrinRepository {
        private List<Brin> repository;


        private BrinRepository(List<Brin> repository) {
            this.repository = repository;
        }


        public List<Brin> getRepository() {
            return repository;
        }


        public void setRepository(List<Brin> repository) {
            this.repository = repository;
        }
    }
}
