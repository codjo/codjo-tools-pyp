package net.codjo.tools.pyp.pages;
import net.codjo.tools.pyp.PypApplication;
import net.codjo.tools.pyp.PypSession;
import net.codjo.tools.pyp.model.filter.BrinFilter;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
/**
 *
 */
public abstract class RootPage extends WebPage {
    private static final String DOCUMENTATION_URL = "http://wp-sic:8080/documentation/framework/agf-pyp.html";


    protected RootPage() {
        this("Bienvenue sur PostYourProblem");
    }

    protected RootPage(String title) {
        add(new Label("title", title));
        add(new ExternalLink("documentationLink", DOCUMENTATION_URL, "Documentation"));
        initLeftPanel("leftPanel");
        initTopPanel("topMenu");
        initRightPanel("rightPanel");
        initBottomPanel();
    }


    protected void initLeftPanel(String id) {
        add(new EmptyPanel(id));
    }


    protected void initTopPanel(String id) {
        add(new EmptyPanel(id));
    }


    protected void initRightPanel(String id) {
        add(new EmptyPanel(id));
    }


    private void initBottomPanel() {
        add(new Label("version", ((PypApplication)getApplication()).getLoader().getApplicationVersion()));
    }


    public PypApplication getMagicApplication() {
        return (PypApplication)getApplication();
    }


    public BrinFilter getBrinFilter() {
        return ((PypSession)getSession()).getBrinFilter();
    }


    public void setBrinFilter(BrinFilter brinFilter) {
        ((PypSession)getSession()).setBrinFilter(brinFilter);
    }
}
