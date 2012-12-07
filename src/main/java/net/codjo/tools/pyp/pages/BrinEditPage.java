package net.codjo.tools.pyp.pages;
import java.io.IOException;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.filter.BrinFilter;
import net.codjo.tools.pyp.model.filter.BrinFilterEnum;
import net.codjo.tools.pyp.pages.HomePage.CallBack;
import net.codjo.tools.pyp.services.BrinService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
public class BrinEditPage extends RootPage {
    private BrinFilter brinFilter;


    public BrinEditPage(final PageParameters parameters) throws IOException {
        BrinService service = BrinService.getBrinService(this);
        Boolean creationMode = parameters.getAsBoolean("creationMode");
        brinFilter = BrinFilterEnum.get(parameters.getString("brinFilter"));

        if (!creationMode) {
            String idString = parameters.getString("id");
            Brin brin = service.getBrin(idString);
            if (brin == null) {
                setResponsePage(new HomePage(brinFilter));
            }
            else {
                buildPage(brin, false);
            }
        }
        else {
            buildPage(new Brin(), creationMode);
        }
    }


    public BrinEditPage(Brin brin, BrinFilter brinFilter) {
        this.brinFilter = brinFilter;
        if (brin != null) {
            buildPage(brin, false);
        }
        else {
            buildPage(new Brin(), true);
        }
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
                return "../../images/backToList.png";
            }
        };

        add(new RightPanel(id, buttonCallBack));
    }


    private void buildPage(Brin brin, boolean creationMode) {
        add(new FeedbackPanel("feedback").setOutputMarkupId(true));
        add(new BrinForm("brinForm", brin, creationMode, brinFilter));
    }
}
