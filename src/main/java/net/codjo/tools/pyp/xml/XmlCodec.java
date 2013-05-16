package net.codjo.tools.pyp.xml;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.ArrayList;
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
        xStream.registerConverter(new BinListConverter("/pyp/pypRepository.xsd"));
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


    private static class BinListConverter implements Converter {
        private String xsdPath;


        private BinListConverter(String xsdPath) {
            this.xsdPath = xsdPath;
        }


        public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
            BrinRepository repository = (BrinRepository)source;
            writer.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.addAttribute("xsi:noNamespaceSchemaLocation", xsdPath);
            writer.startNode("repository");
            final List<Brin> brinList = repository.getRepository();
            for (Brin brin : brinList) {
                writer.startNode("brin");
                context.convertAnother(brin);
                writer.endNode();
            }
            writer.endNode();
        }


        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            BrinRepository repository = new BrinRepository(new ArrayList<Brin>());
            List<Brin> brinlist= new ArrayList<Brin>();
            reader.moveDown();
            while (reader.hasMoreChildren()) {
                reader.moveDown();
                Brin brin = (Brin)context.convertAnother(repository, Brin.class);
                brinlist.add(brin);
                reader.moveUp();
            }
            repository.setRepository(brinlist);
            return repository;
        }


        public boolean canConvert(Class type) {
            return type.equals(BrinRepository.class); 
        }
    }
}
