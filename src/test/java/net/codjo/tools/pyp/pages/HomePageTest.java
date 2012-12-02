package net.codjo.tools.pyp.pages;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import net.codjo.test.common.Directory.NotDeletedException;
import net.codjo.test.common.fixture.DirectoryFixture;
import net.codjo.tools.pyp.WicketFixture;
import net.codjo.util.file.FileUtil;
import org.apache.wicket.Session;
import org.apache.wicket.util.tester.FormTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 *
 */
public class HomePageTest extends WicketFixture {
    DirectoryFixture fixture = new DirectoryFixture("target/pyp");
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S z");


    @Before
    public void setUp() throws NotDeletedException {
        fixture.doSetUp();
        doInit("/pyp.properties");
    }


    @After
    public void tearDown() throws NotDeletedException {
        fixture.doTearDown();
    }


    @Test
    public void test_homePage() throws Exception {
        getWicketTester().startPage(HomePage.class);
        getWicketTester().assertContains("Title");
        getWicketTester().assertContains("Creation");
        getWicketTester().assertContains("Status");
        getWicketTester().assertContains("Add new BRIN");

        assertTextIsNotPresent("thisIsA new Brin");

        getWicketTester().assertLabel("leftPanel:leftContainer:infoList:0:statusLabel", "current");
        getWicketTester().assertLabel("leftPanel:leftContainer:infoList:1:statusLabel", "unblocked");
        getWicketTester().assertLabel("leftPanel:leftContainer:infoList:2:statusLabel", "toEradicate");
        getWicketTester().assertLabel("leftPanel:leftContainer:infoList:3:statusLabel", "eradicated");

        getWicketTester().assertLabel("leftPanel:leftContainer:infoList:0:nbBrin", "0");

        addNewBrin("thisIsA new Brin", 2);

        getWicketTester().assertRenderedPage(HomePage.class);
        getWicketTester().assertLabel("leftPanel:leftContainer:infoList:2:nbBrin", "1");
        getWicketTester().assertContains("thisIsA new Brin");
        getWicketTester().assertContains("toEradicate");

        updateBrin("titre Modifié", 2);
        getWicketTester().assertRenderedPage(HomePage.class);
        getWicketTester().assertContains("titre Modifié");
        getWicketTester().assertContains("toEradicate");
    }


    @Test
    public void test_BrinFilter() throws Exception {
        Date today = Calendar.getInstance().getTime();
        Date twoDaysAgo = shiftDate(today, -2);
        Date sevenDaysAgo = shiftDate(today, -7);
        Date aMonthAgo = shiftDate(today, -30);
        String fileContent = "<brinList>\n"
                             + "  <repository>\n"
                             + "    <brin>\n"
                             + "      <uuid>1</uuid>\n"
                             + "      <title>Brin de plus d'un mois</title>\n"
                             + "      <creationDate>" + simpleDateFormat.format(aMonthAgo) + "</creationDate>\n"
                             + "      <status>current</status>\n"
                             + "      <description>sdq</description>\n"
                             + "      <affectedTeams/>\n"
                             + "      <unblockingDescription>qsd</unblockingDescription>\n"
                             + "    </brin>\n"
                             + "    <brin>\n"
                             + "      <uuid>2</uuid>\n"
                             + "      <title>Brin d'il y a deux jours</title>\n"
                             + "      <creationDate>" + simpleDateFormat.format(twoDaysAgo) + "</creationDate>\n"
                             + "      <status>current</status>\n"
                             + "      <affectedTeams/>\n"
                             + "      <unblockingDescription>sqdqd</unblockingDescription>\n"
                             + "    </brin>\n"
                             + "    <brin>\n"
                             + "      <uuid>3</uuid>\n"
                             + "      <title>Brin pile ya une semaine</title>\n"
                             + "      <creationDate>" + simpleDateFormat.format(sevenDaysAgo) + "</creationDate>\n"
                             + "      <status>current</status>\n"
                             + "      <affectedTeams/>\n"
                             + "      <unblockingDescription>sqdqd</unblockingDescription>\n"
                             + "    </brin>\n"
                             + "  </repository>\n"
                             + "</brinList>";

        FileUtil.saveContent(new File(fixture.getCanonicalFile(), "pypRpository.xml"), fileContent);
        //TODO not very beautifull to load repository, we need to re-init
        doInit("/pyp.properties");

        getWicketTester().startPage(HomePage.class);

        getWicketTester().assertLabel("brinListContainer:brinList:1:title", "Brin d&#039;il y a deux jours");
        getWicketTester().assertLabel("brinListContainer:brinList:2:title", "Brin pile ya une semaine");
        getWicketTester().assertLabel("brinListContainer:brinList:3:title", "Brin de plus d&#039;un mois");

        switchFilter(this);

        //TODO verifier pourquoi c'est indice 8 et 9 !!!!!!!!!!!!
        getWicketTester().dumpPage();
        getWicketTester().assertLabel("brinListContainer:brinList:8:title", "Brin pile ya une semaine");
        getWicketTester().assertLabel("brinListContainer:brinList:7:title", "Brin d&#039;il y a deux jours");
        assertTextIsNotPresent("Brin de plus d&#039;un mois");

    }


    //TODO copierColler de BrinFilterForm pas beau
    public static Date shiftDate(Date dateToShift, int nbOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToShift);
        calendar.add(Calendar.DAY_OF_MONTH, nbOfDays);
        return calendar.getTime();
    }


    private void addNewBrin(String title, int statusIndex) {
        getWicketTester().assertRenderedPage(HomePage.class);
        getWicketTester().clickLink("rightPanel:myContainer:menuList:0:imageLink");
        getWicketTester().assertRenderedPage(BrinEditPage.class);

        getWicketTester().setParameterForNextRequest("brinForm:title", title);
        getWicketTester().setParameterForNextRequest("brinForm:status", statusIndex);
        getWicketTester().submitForm("brinForm");
        getWicketTester().assertErrorMessages(new String[]{"Le champ 'creationDate' est obligatoire."});

        Session.get().cleanupFeedbackMessages();
        getWicketTester().setParameterForNextRequest("brinForm:title", title);
        getWicketTester().setParameterForNextRequest("brinForm:status", statusIndex);
        getWicketTester().setParameterForNextRequest("brinForm:creationDate", "2001-01-12");
        getWicketTester().submitForm("brinForm");

        getWicketTester().assertNoErrorMessage();
    }


    private void updateBrin(String title, int statusIndex) {
        getWicketTester().clickLink("brinListContainer:brinList:1:editBrin");
        getWicketTester().assertRenderedPage(BrinEditPage.class);

        getWicketTester().setParameterForNextRequest("brinForm:title", title);
        getWicketTester().setParameterForNextRequest("brinForm:status", statusIndex);
        getWicketTester().setParameterForNextRequest("brinForm:creationDate", "2001-01-12");

        getWicketTester().submitForm("brinForm");

        getWicketTester().assertNoErrorMessage();
    }


    private void switchFilter(WicketFixture fixture) {
        FormTester tester = fixture.newFormTester("rightPanel:myContainer:filterForm");
        tester.select("brinFilters", 0);
        tester.submit();
        fixture.executeAjaxEvent("rightPanel:myContainer:filterForm:brinFilters", "onchange");
    }
}
