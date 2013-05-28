package net.codjo.tools.pyp.xml;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import net.codjo.test.common.XmlUtil;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Team;
import org.joda.time.DateTime;
import org.junit.Test;

import static net.codjo.test.common.matcher.JUnitMatchers.*;
/**
 *
 */
public class XmlCodecTest {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private XmlCodec xmlCodec = new XmlCodec();


    @Test
    public void test_toXml() throws Exception {
        List<Brin> brins = new ArrayList<Brin>();

        Brin brin = new Brin("titre1");
        brin.setUuid("MonUUID");
        brin.setCreationDate(SIMPLE_DATE_FORMAT.parse("2011-06-01"));
        brin.setDescription("description1");
        brins.add(brin);

        Brin titre2 = new Brin("titre2");
        titre2.setCreationDate(SIMPLE_DATE_FORMAT.parse("2011-06-01"));
        titre2.setDescription("description2");
        List<Team> affectedTeams = new ArrayList<Team>();
        affectedTeams.add(Team.gacpa);
        affectedTeams.add(Team.wam);
        titre2.setAffectedTeams(affectedTeams);
        brins.add(titre2);

        Brin brinTrois = new Brin("titre3");
        brinTrois.setUuid("brin3");
        brinTrois.setCreationDate(SIMPLE_DATE_FORMAT.parse("2011-06-01"));
        brinTrois.setDescription("description3");
        brinTrois.setUnBlockingDate(SIMPLE_DATE_FORMAT.parse("2011-12-15"));
        brinTrois.setEradicationDate(SIMPLE_DATE_FORMAT.parse("2011-05-23"));
        brins.add(brinTrois);

        String actual = xmlCodec.toXml(brins);

        XmlUtil.assertEquivalent("<brinList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                                 + "          xsi:noNamespaceSchemaLocation=\"/pyp/pypRepository.xsd\">"
                                 + "  <repository>\n"
                                 + "    <brin>\n"
                                 + "      <uuid>MonUUID</uuid>\n"
                                 + "      <title>titre1</title>\n"
                                 + "      <creationDate>01/06/2011</creationDate>\n"
                                 + "      <status>current</status>\n"
                                 + "      <description>description1</description>\n"
                                 + "      <affectedTeams/>\n"
                                 + "    </brin>\n"
                                 + "    <brin>\n"
                                 + "      <title>titre2</title>\n"
                                 + "      <creationDate>01/06/2011</creationDate>\n"
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
                                 + "      <creationDate>01/06/2011</creationDate>\n"
                                 + "      <status>current</status>\n"
                                 + "      <description>description3</description>\n"
                                 + "      <affectedTeams/>\n"
                                 + "      <unblockingDate>15/12/2011</unblockingDate>"
                                 + "      <eradicationDate>23/05/2011</eradicationDate>"
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
                          + "      <creationDate>01/06/2011</creationDate>\n"
                          + "      <status>current</status>\n"
                          + "      <description>description1</description>\n"
                          + "      <affectedTeams/>\n"
                          + "      <unblockingType>medium</unblockingType>\n"
                          + "      <unblockingDate>01/06/2011</unblockingDate>\n"
                          + "      <unblockingDescription>Bon ben c'est bon</unblockingDescription>\n"
                          + "    </brin>\n"
                          + "    <brin>\n"
                          + "      <title>titre2</title>\n"
                          + "      <creationDate>01/06/2011</creationDate>\n"
                          + "      <status>current</status>\n"
                          + "      <description>description2</description>\n"
                          + "      <affectedTeams>\n"
                          + "      <team>wam</team>\n"
                          + "      <team>transverse</team>\n"
                          + "      </affectedTeams>\n"
                          + "      <rootCause>Pas d'électricité</rootCause>\n"
                          + "      <eradicationDate>01/06/2011</eradicationDate>"
                          + "    </brin>"
                          + "  </repository>\n"
                          + "</brinList>";

        String actual = xmlCodec.toXml(xmlCodec.fromXml(expected));
        XmlUtil.assertEquivalent(expected, actual);
    }


    @Test
    public void test_canReadBothDateFormat() throws Exception {
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
                          + "      <unblockingDate>12/06/2011</unblockingDate>\n"
                          + "      <eradicationDate>2011-07-28 00:00:00.0 CEST</eradicationDate>"
                          + "      <unblockingDescription>Bon ben c'est bon</unblockingDescription>\n"
                          + "    </brin>\n"
                          + "  </repository>\n"
                          + "</brinList>";

        final List<Brin> brinList = xmlCodec.fromXml(expected);

        final Brin brin = brinList.get(0);
        assertThat(brin.getCreationDate(), is(new DateTime(2011, 6, 1, 0, 0, 0, 0).toDate()));
        assertThat(brin.getUnBlockingDate(), is(new DateTime(2011, 6, 12, 0, 0, 0, 0).toDate()));
        assertThat(brin.getEradicationDate(), is(new DateTime(2011, 7, 28, 0, 0, 0, 0).toDate()));
    }
}
