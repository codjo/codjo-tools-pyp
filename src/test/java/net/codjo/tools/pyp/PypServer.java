package net.codjo.tools.pyp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mortbay.jetty.webapp.WebAppContext;
/**
 *
 */
public class PypServer {

    private PypServer() {
    }


    public static void main(String[] args) {
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
