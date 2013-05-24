package net.codjo.tools.pyp.xml;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import net.codjo.test.common.XmlUtil;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Team;
import org.junit.Test;
/**
 *
 */
public class XmlCodecTest {
    private XmlCodec xmlCodec = new XmlCodec();


    @Test
    public void test_toXml() throws Exception {
        List<Brin> brins = new ArrayList<Brin>();

        Brin brin = new Brin("titre1");
        brin.setUuid("MonUUID");
        brin.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-06-01"));
        brin.setDescription("description1");
        brins.add(brin);

        Brin titre2 = new Brin("titre2");
        titre2.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-06-01"));
        titre2.setDescription("description2");
        List<Team> affectedTeams = new ArrayList<Team>();
        affectedTeams.add(Team.gacpa);
        affectedTeams.add(Team.wam);
        titre2.setAffectedTeams(affectedTeams);
        brins.add(titre2);

        Brin brinTrois = new Brin("titre3");
        brinTrois.setUuid("brin3");
        brinTrois.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-06-01"));
        brinTrois.setDescription("description3");
        brinTrois.setUnBlockingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-12-15"));
        brinTrois.setEradicationDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-23"));
        brins.add(brinTrois);

        String actual = xmlCodec.toXml(brins);

        XmlUtil.assertEquivalent("<brinList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                                 + "          xsi:noNamespaceSchemaLocation=\"/pyp/pypRepository.xsd\">"
                                 + "  <repository>\n"
                                 + "    <brin>\n"
                                 + "      <uuid>MonUUID</uuid>\n"
                                 + "      <title>titre1</title>\n"
                                 + "      <creationDate>2011-06-01 00:00:00.0 CEST</creationDate>\n"
                                 + "      <status>current</status>\n"
                                 + "      <description>description1</description>\n"
                                 + "      <affectedTeams/>\n"
                                 + "    </brin>\n"
                                 + "    <brin>\n"
                                 + "      <title>titre2</title>\n"
                                 + "      <creationDate>2011-06-01 00:00:00.0 CEST</creationDate>\n"
                                 + "      <status>current</status>\n"
                                 + "      <description>description2</description>\n"
                                 + "      <affectedTeams>\n"
                                 + "           <team>gacpa</team>\n"
                                 + "           <team>wam</team>\n"
                                 + "      </affectedTeams>\n"
                                 + "    </brin>"
                                 + "    <brin>\n"
                                 + "      <uuid>brin3</uuid>\n"
                                 + "      <title>titre3</title>\n"
                                 + "      <creationDate>2011-06-01 00:00:00.0 CEST</creationDate>\n"
                                 + "      <status>current</status>\n"
                                 + "      <description>description3</description>\n"
                                 + "      <affectedTeams/>\n"
                                 + "      <unblockingDate>2011-12-15 00:00:00.0 CET</unblockingDate>"
                                 + "      <eradicationDate>2011-05-23 00:00:00.0 CEST</eradicationDate>"
                                 + "    </brin>\n"
                                 + "  </repository>\n"
                                 + "</brinList>", actual);
    }


    @Test
    public void test_fromXml() throws Exception {
        String expected = "<brinList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                          + "          xsi:noNamespaceSchemaLocation=\"/pyp/pypRepository.xsd\">"
                          + "  <repository>\n"
                          + "    <brin>\n"
                          + "      <uuid>uniqueUuid</uuid>\n"
                          + "      <title>titre1</title>\n"
                          + "      <creationDate>2011-06-01 00:00:00.0 CEST</creationDate>\n"
                          + "      <status>current</status>\n"
                          + "      <description>description1</description>\n"
                          + "      <affectedTeams/>\n"
                          + "      <unblockingType>medium</unblockingType>\n"
                          + "      <unblockingDate>2011-06-01 00:00:00.0 CEST</unblockingDate>\n"
                          + "      <unblockingDescription>Bon ben c'est bon</unblockingDescription>\n"
                          + "    </brin>\n"
                          + "    <brin>\n"
                          + "      <title>titre2</title>\n"
                          + "      <creationDate>2011-06-01 00:00:00.0 CEST</creationDate>\n"
                          + "      <status>current</status>\n"
                          + "      <description>description2</description>\n"
                          + "      <affectedTeams>\n"
                          + "      <team>wam</team>\n"
                          + "      <team>transverse</team>\n"
                          + "      </affectedTeams>\n"
                          + "      <rootCause>Pas d'électricité</rootCause>\n"
                          + "      <eradicationDate>2012-06-23 00:00:00.0 CEST</eradicationDate>"
                          + "    </brin>"
                          + "  </repository>\n"
                          + "</brinList>";

        String actual = xmlCodec.toXml(xmlCodec.fromXml(expected));

        XmlUtil.assertEquivalent(expected, actual);
    }
}
