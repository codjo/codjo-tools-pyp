package net.codjo.tools.pyp.pages;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.codjo.tools.pyp.ExternalImage;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.pages.HomePage.CallBack;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
/**
 *
 */
public class RightPanel extends Panel {
    WebMarkupContainer myContainer;


    public RightPanel(String id, final CallBack... buttonCallBack) {
        super(id);
        myContainer = new WebMarkupContainer("myContainer");
        add(myContainer);

        //TODO A proprifier ! le menu ne doit pas apparaitre lors de l'edition d'un brin
        CallBack brinFilterCallBack = null;
        List<CallBack<Brin>> resultList = new ArrayList<CallBack<Brin>>();
        for (CallBack callBack : Arrays.asList(buttonCallBack)) {
            if ("brinFilterCallBack".equals(callBack.getLabel())) {
                brinFilterCallBack = callBack;
            }
            else {
                resultList.add(callBack);
            }
        }

        ListView<CallBack> listView = new ListView<CallBack>("menuList", resultList) {
            @Override
            protected void populateItem(ListItem<CallBack> callBackListItem) {
                addLink(callBackListItem, callBackListItem.getModelObject());
            }
        };
        myContainer.add(listView);
        myContainer.add(brinFilterForm(brinFilterCallBack));
    }


    private Component brinFilterForm(CallBack brinFilter) {
        return new BrinFilterForm("filterForm", brinFilter).setVisible(brinFilter!=null);
    }


    private void addLink(ListItem<CallBack> callBackListItem, final CallBack buttonCallBack) {
        Link link = new Link("imageLink") {
            @Override
            protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
                getResponse().write(buttonCallBack.getLabel());
                super.onComponentTagBody(markupStream, openTag);
            }


            @Override
            public void onClick() {
                buttonCallBack.onClickCallBack(new Brin());
            }
        };
        link.add(new ExternalImage("imageLogo", buttonCallBack.getImagePath()));
        callBackListItem.add(link);
    }
}

