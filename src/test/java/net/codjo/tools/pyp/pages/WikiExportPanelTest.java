package net.codjo.tools.pyp.pages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import net.codjo.test.common.fixture.CompositeFixture;
import net.codjo.test.common.fixture.DirectoryFixture;
import net.codjo.test.common.fixture.MailFixture;
import net.codjo.tools.pyp.WicketFixture;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 *
 */
public class WikiExportPanelTest extends WicketFixture {

    @Test
    public void test_addWikiContentForStatus() throws Exception {
        doInit("/pyp.properties");
        WikiExportPanel wikiExportPanel = new WikiExportPanel("WikiExportPanel");

        StringBuilder builder = new StringBuilder();
        Map<Status, List<Brin>> resultMap = new HashMap<Status, List<Brin>>();
        Status status = Status.current;
        resultMap.put(status, new ArrayList<Brin>());

        Brin brin = new Brin("Easy title");
        brin.setStatus(status);
        brin.setUuid("1");
        resultMap.get(status).add(brin);

        brin = new Brin("Title with [braquette]");
        brin.setStatus(status);
        brin.setUuid("2");
        resultMap.get(status).add(brin);

        wikiExportPanel.addWikiContentForStatus(builder, resultMap, status);

        Assert.assertEquals("* current\n"
                            + "** [Easy title | http://localhost/edit.html?id=1]\n"
                            + "** [Title with \\[braquette\\] | http://localhost/edit.html?id=2]\n",
                            builder.toString());
    }
}
