package net.codjo.tools.pyp.pages;
import java.io.File;
import java.util.Date;
import net.codjo.test.common.fixture.DirectoryFixture;
import net.codjo.tools.pyp.WicketFixture;
import net.codjo.tools.pyp.model.Status;
import net.codjo.util.file.FileUtil;
import org.apache.wicket.PageParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 *
 */
public class BrinEditPageTest extends WicketFixture {
    private DirectoryFixture fixture = new DirectoryFixture("target/pyp");


    @Before
    public void setUp() throws Exception {
        fixture.doSetUp();
    }


    @After
    public void tearDown() throws Exception {
        fixture.doTearDown();
    }


    @Test
    public void test_accessByLinkByPageParameters() throws Exception {
                String fileContent = "<brinList>\n"
                             + "  <repository>\n"
                             + "    <brin>\n"
                             + "      <uuid>1</uuid>\n"
                             + "      <title>Brin de plus d'un mois</title>\n"
                             + "      <creationDate>2012-12-11 00:00:00.0 CET</creationDate>\n"
                             + "      <status>unblocked</status>\n"
                             + "      <description>my Description</description>\n"
                             + "      <affectedTeams/>\n"
                             + "      <unblockingDescription>qsd</unblockingDescription>\n"
                             + "    </brin>\n"
                             + "  </repository>\n"
                             + "</brinList>";

        FileUtil.saveContent(new File(fixture.getCanonicalFile(), "pypRepository.xml"), fileContent);
        doInit("/pyp.properties");

        final PageParameters parameters = new PageParameters("id=1");
        getWicketTester().startPage(BrinEditPage.class, parameters);
        getWicketTester().assertModelValue("brinForm:title", "Brin de plus d'un mois");
        getWicketTester().assertModelValue("brinForm:creationDate", new Date("12/11/2012"));
        getWicketTester().assertModelValue("brinForm:description", "my Description");
        getWicketTester().assertModelValue("brinForm:status", Status.unblocked);
    }
}
