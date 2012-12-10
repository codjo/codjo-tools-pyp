package net.codjo.tools.pyp.pages;
import java.io.IOException;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.filter.BrinFilter;
import net.codjo.tools.pyp.pages.HomePage.CallBack;
import net.codjo.tools.pyp.services.BrinService;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
public class BrinEditPage extends RootPage {
    /**
     * Keep this constructor to access brin directly from url (cf mail content)
     *  ex : http://localhost:8080/pyp/edit.html?id=1245b4d5-9c64-41ed-b3df-d5f327234979
     *
     * @throws IOException
     */
    public BrinEditPage() throws IOException {
        String brinId = getRequest().getParameter("id");
        Brin brin = BrinService.getBrinService(this).getBrin(brinId);
        buildPage(brin);
    }

    public BrinEditPage(Brin brin, BrinFilter brinFilter) {
        this.brinFilter = brinFilter;
        buildPage(brin);
    }


    @Override
    protected void initRightPanel(String id) {
        CallBack buttonCallBack = new CallBack<Brin>() {
            public void onClickCallBack(Brin brin) {
                setResponsePage(new HomePage(brinFilter));
            }


            public String getLabel() {
                return "Back to the List";
            }


            public String getImagePath() {
                return "images/backToList.png";
            }
        };

        add(new RightPanel(id, buttonCallBack));
    }


    private void buildPage(Brin brin) {
        add(new FeedbackPanel("feedback").setOutputMarkupId(true));
        if(brin==null){
            brin= new Brin();
        }
        add(new BrinForm("brinForm", brin, brinFilter));
    }
}
