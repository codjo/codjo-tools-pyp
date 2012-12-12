package net.codjo.tools.pyp.pages;
import java.io.IOException;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.filter.BrinFilter;
import net.codjo.tools.pyp.services.BrinService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
public class BrinEditPage extends RootPage {
    private CallBack buttonCallBack;


    /**
     * Keep this constructor to access brin directly from url (cf mail content) ex :
     * http://localhost:8080/pyp/edit.html/id/1245b4d5-9c64-41ed-b3df-d5f327234979
     */
    public BrinEditPage(PageParameters pageParameters) throws IOException {
        String brinId = getRequest().getParameter("id");
        if (brinId==null){
            brinId=pageParameters.getString("id");
        }
        Brin brin = BrinService.getBrinService(this).getBrin(brinId);
        buildPage(brin);
    }

    public BrinEditPage(Brin brin, BrinFilter brinFilter) {
        this.brinFilter = brinFilter;
        buildPage(brin);
    }


    @Override
    protected void initRightPanel(String id) {
        CallBack<AjaxRequestTarget> buttonCallBack = new AbstractCallBack<AjaxRequestTarget>("Back to the List",
                                                                                             "images/backToList.png") {
            public void onClickCallBack(AjaxRequestTarget brin) {
                setResponsePage(new HomePage(brinFilter));
            }
        };

        add(new RightPanel(id, buttonCallBack));
    }


    private void buildPage(Brin brin) {
        add(new FeedbackPanel("feedback").setOutputMarkupId(true));
        if (brin == null) {
            brin = new Brin();
        }
        add(new BrinForm("brinForm", brin, brinFilter));
    }
}
