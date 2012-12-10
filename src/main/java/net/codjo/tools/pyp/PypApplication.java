package net.codjo.tools.pyp;
import net.codjo.tools.pyp.pages.BrinEditPage;
import net.codjo.tools.pyp.pages.HomePage;
import net.codjo.tools.pyp.services.BrinService;
import net.codjo.tools.pyp.services.PropertyLoader;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
/**
 *
 */
public class PypApplication extends WebApplication {
    private PropertyLoader loader;
    private final BrinService brinService;


    @Override
    public Session newSession(Request request, Response response) {
        return super.newSession(request, response);
    }


    public PypApplication(String propertyFilePath) {
        loader = new PropertyLoader(propertyFilePath);
        brinService = new BrinService(loader.getRepositoryFilePath());
    }


    @Override
    public String getConfigurationType() {
        String configurationType = loader.getEnvironmentMode();
        if (DEVELOPMENT.equalsIgnoreCase(configurationType)) {
            return Application.DEVELOPMENT;
        }
        else if (DEPLOYMENT.equalsIgnoreCase(configurationType)) {
            return Application.DEPLOYMENT;
        }
        return Application.DEVELOPMENT;
    }


    public PypApplication() {
        this("/pyp.properties");
    }


    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }


    @Override
    protected void init() {
        super.init();
        mountBookmarkablePage("/edit.html", BrinEditPage.class);
        mountBookmarkablePage("/home.html", HomePage.class);
    }


    public PropertyLoader getLoader() {
        return loader;
    }


    public BrinService getBrinService() {
        return brinService;
    }
}
