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

        assertLabelInLeftPanelAtRow(1,"current","0");
        assertLabelInLeftPanelAtRow(2,"unblocked","0");
        assertLabelInLeftPanelAtRow(3,"toEradicate","0");
        assertLabelInLeftPanelAtRow(4, "eradicated", "0");

        addNewBrin("thisIsA new Brin", 2);

        getWicketTester().assertRenderedPage(HomePage.class);
        getWicketTester().assertLabel("leftPanel:summaryPanel:infoList:3:nbBrin", "1");
        getWicketTester().assertContains("thisIsA new Brin");
        getWicketTester().assertContains("toEradicate");

        updateBrin("titre Modifié", 2);
        getWicketTester().assertRenderedPage(HomePage.class);
        getWicketTester().assertContains("titre Modifié");
        getWicketTester().assertContains("toEradicate");

        assertLabelInLeftPanelAtRow(1,"current","0");
        assertLabelInLeftPanelAtRow(2,"unblocked","0");
        assertLabelInLeftPanelAtRow(3,"toEradicate","1");
        assertLabelInLeftPanelAtRow(4,"eradicated","0");
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
                             + "      <status>unblocked</status>\n"
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
                             + "    <brin>\n"
                             + "      <uuid>4</uuid>\n"
                             + "      <title>Plus d'un mois MAIS status current</title>\n"
                             + "      <creationDate>" + simpleDateFormat.format(aMonthAgo) + "</creationDate>\n"
                             + "      <status>current</status>\n"
                             + "      <description>sdq</description>\n"
                             + "      <affectedTeams/>\n"
                             + "      <unblockingDescription>qsd</unblockingDescription>\n"
                             + "    </brin>\n"
                             + "    <brin>\n"
                             + "      <uuid>5</uuid>\n"
                             + "      <title>Deboque ya moins d une semaine</title>\n"
                             + "      <creationDate>" + simpleDateFormat.format(aMonthAgo) + "</creationDate>\n"
                             + "      <unblockingDate>" + simpleDateFormat.format(twoDaysAgo) + "</unblockingDate>\n"
                             + "      <status>unblocked</status>\n"
                             + "      <description>sdq</description>\n"
                             + "      <affectedTeams/>\n"
                             + "      <unblockingDescription>qsd</unblockingDescription>\n"
                             + "    </brin>\n"
                             + "  </repository>\n"
                             + "</brinList>";

        FileUtil.saveContent(new File(fixture.getCanonicalFile(), "pypRpository.xml"), fileContent);
        //TODO not very beautifull to load repository, we need to re-init
        doInit("/pyp.properties");

        getWicketTester().startPage(HomePage.class);

        int row = 1;
        assertLabelAtRow(row++, "Brin d&#039;il y a deux jours");
        assertLabelAtRow(row++, "Brin pile ya une semaine");
        assertLabelAtRow(row++, "Plus d&#039;un mois MAIS status current");
        assertLabelAtRow(row++, "Brin de plus d&#039;un mois");
        assertLabelAtRow(row, "Deboque ya moins d une semaine");

        assertLabelInLeftPanelAtRow(1,"current","3");
        assertLabelInLeftPanelAtRow(2,"unblocked","2");
        assertLabelInLeftPanelAtRow(3,"toEradicate","0");
        assertLabelInLeftPanelAtRow(4,"eradicated","0");

        switchFilter(this, 0);

        getWicketTester().dumpPage();

        //TODO decalage des indices de la liste a cause de 2 appels a la construction de la liste ?
        row = 11;
        assertLabelAtRow(row++, "Brin d&#039;il y a deux jours");
        assertLabelAtRow(row++, "Brin pile ya une semaine");
        assertLabelAtRow(row++, "Plus d&#039;un mois MAIS status current");
        assertLabelAtRow(row, "Deboque ya moins d une semaine");
        assertTextIsNotPresent("Brin de plus d&#039;un mois");

        assertLabelInLeftPanelAtRow(9,"current","3");
        assertLabelInLeftPanelAtRow(10,"unblocked","1");
        assertLabelInLeftPanelAtRow(11,"toEradicate","0");
        assertLabelInLeftPanelAtRow(12,"eradicated","0");

        switchFilter(this, 1);

        assertLabelInLeftPanelAtRow(17,"current","3");
        assertLabelInLeftPanelAtRow(18,"unblocked","2");
        assertLabelInLeftPanelAtRow(19,"toEradicate","0");
        assertLabelInLeftPanelAtRow(20, "eradicated", "0");

    }


    private void assertLabelAtRow(int row, String expectedLabel) {
        getWicketTester().assertLabel("brinListContainer:brinList:" + row + ":title",
                                      expectedLabel);
    }


    private void assertLabelInLeftPanelAtRow(int row, String expectedLabelText, String expectedBrinNumber) {
        getWicketTester().assertLabel("leftPanel:summaryPanel:infoList:" + row + ":statusLabel", expectedLabelText);
        getWicketTester().assertLabel("leftPanel:summaryPanel:infoList:" + row + ":nbBrin",
                                      expectedBrinNumber);
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


    private void switchFilter(WicketFixture fixture, int index) {
        FormTester tester = fixture.newFormTester("leftPanel:filterForm");
        tester.select("brinFilters", index);
        tester.submit();
        fixture.executeAjaxEvent("leftPanel:filterForm:brinFilters", "onchange");
    }
}
