package net.codjo.tools.pyp.pages;
import java.io.IOException;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.services.BrinService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.protocol.http.WebApplication;
public class BrinEditPage extends RootPage {
    static final String BRIN_ID_KEY = "id";


    /**
     * Keep this constructor to access brin directly from url (cf mail content) ex :
     * http://localhost:8080/pyp/edit.html/id/1245b4d5-9c64-41ed-b3df-d5f327234979
     *
     * @param pageParameters: unique id of the brin
     *
     * @throws java.io.IOException: @see net.codjo.tools.pyp.services.BrinService
     */
    @SuppressWarnings({"UnusedDeclaration"})
    public BrinEditPage(PageParameters pageParameters) throws IOException {
        String brinId = getRequest().getParameter(BRIN_ID_KEY);
        if (brinId == null) {
            brinId = pageParameters.getString(BRIN_ID_KEY);
        }
        Brin brin = BrinService.getBrinService(this).getBrin(brinId);
        buildPage(brin);
    }


    @Override
    protected void initRightPanel(String id) {
        String contextPath = WebApplication.get().getServletContext().getContextPath();
        CallBack<AjaxRequestTarget> buttonCallBack = new AbstractCallBack<AjaxRequestTarget>("Back to the List",
                                                                                             contextPath
                                                                                             + "/images/backToList.png") {
            public void onClickCallBack(AjaxRequestTarget brin) {
                setResponsePage(HomePage.class);
            }
        };
        add(new RightPanel(id, buttonCallBack));
    }


    private void buildPage(Brin brin) {
        add(new FeedbackPanel("feedback").setOutputMarkupId(true));
        if (brin == null) {
            brin = new Brin();
        }
        add(new BrinForm("brinForm", brin));
    }
}
