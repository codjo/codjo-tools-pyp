package net.codjo.tools.pyp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.codjo.test.common.fixture.MailFixture;
import org.mortbay.jetty.webapp.WebAppContext;
/**
 * TODO add a clean "stop" method
 */
public class PypServer {

    private PypServer() {
    }


    public static void main(String[] args) {
        MailFixture mailFixture = new MailFixture(89);
        mailFixture.doSetUp();

        JettyFixture fixture = new JettyFixture(8080) {
            @Override
            protected void handleHttpRequest(String target, HttpServletRequest request, HttpServletResponse response) {
                //Do nothing
            }
        };

        try {
            WebAppContext wah = new WebAppContext();
            wah.setContextPath("/pyp");
            wah.setWar("src/main/webapp");
            fixture.getServer().setHandler(wah);
            fixture.doSetUp();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
