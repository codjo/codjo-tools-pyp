package net.codjo.tools.pyp;

import net.codjo.tools.pyp.pages.BrinEditPage;
import net.codjo.tools.pyp.pages.HomePage;
import net.codjo.tools.pyp.services.BrinService;
import net.codjo.tools.pyp.services.MailService;
import net.codjo.tools.pyp.services.PropertyLoader;
import org.apache.wicket.*;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

import java.io.File;
import java.nio.charset.Charset;

/**
 *
 */
public class PypApplication extends WebApplication {
    protected static final String XML_REPOSITORY_PATH = "/brinList";
    private PropertyLoader loader;
    private final BrinService brinService;
    private MailService mailService;


    public PypApplication() {
        this("/pyp.properties");
    }


    public PypApplication(String propertyFilePath) {
        loader = new PropertyLoader(propertyFilePath);
        brinService = new BrinService(loader.getRepositoryFilePath());
        mailService = new MailService();
    }


    @Override
    public Session newSession(Request request, Response response) {
        return new PypSession(request);
    }


    @Override
    public String getConfigurationType() {
        String configurationType = loader.getEnvironmentMode();
        if (DEVELOPMENT.equalsIgnoreCase(configurationType)) {
            return Application.DEVELOPMENT;
        } else if (DEPLOYMENT.equalsIgnoreCase(configurationType)) {
            return Application.DEPLOYMENT;
        }
        return Application.DEVELOPMENT;
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
        mountXmlRepositoryResource();
    }


    private void mountXmlRepositoryResource() {
        final File file = new File(loader.getRepositoryFilePath());
        final String resourceId = "xmlRepository";
        getSharedResources().add(resourceId, new WebResource() {
            @Override
            public IResourceStream getResourceStream() {
                final FileResourceStream fileResourceStream = new FileResourceStream(file);
                fileResourceStream.setCharset(Charset.forName("UTF-8"));
                return fileResourceStream;
            }
        });
        mountSharedResource(XML_REPOSITORY_PATH, Application.class.getName() + "/" + resourceId);
    }


    public PropertyLoader getLoader() {
        return loader;
    }


    public BrinService getBrinService() {
        return brinService;
    }

    public MailService getMailService() {
        return mailService;
    }
}