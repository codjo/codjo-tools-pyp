package net.codjo.tools.pyp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.codjo.test.common.fixture.CompositeFixture;
import net.codjo.test.common.fixture.MailFixture;
import org.mortbay.jetty.webapp.WebAppContext;

public class PypServer {
    public final static String CONTEXT_PATH = "/pyp";
    public final static int WEB_PORT = 8080;
    public static int MAIL_PORT = 89;
    private static MailFixture mailFixture = new MailFixture(MAIL_PORT);
    private static JettyFixture fixture = new JettyFixture(WEB_PORT) {
        @Override
        protected void handleHttpRequest(String target, HttpServletRequest request, HttpServletResponse response) {
            //Do nothing
        }
    };
    private static CompositeFixture compositeFixture = new CompositeFixture(fixture, mailFixture);



    private PypServer() {
    }


    public static void main(String[] args) {

        try {
            WebAppContext wah = new WebAppContext();
            wah.setContextPath(CONTEXT_PATH);
            wah.setWar("src/main/webapp");
            fixture.getServer().setHandler(wah);
            compositeFixture.doSetUp();
        }
        catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }


    public static void stopServer() throws Exception {
        compositeFixture.doTearDown();
    }


    public static String getServerUrl() {
        return "http://localhost:" + WEB_PORT + CONTEXT_PATH;
    }
}
