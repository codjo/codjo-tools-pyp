package net.codjo.tools.pyp.pages;

import java.util.Arrays;
import net.codjo.tools.pyp.ExternalImage;
import net.codjo.tools.pyp.pages.HomePage.CallBack;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
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

        ListView<CallBack> listView = new ListView<CallBack>("menuList", Arrays.asList(buttonCallBack)) {
            @Override
            protected void populateItem(ListItem<CallBack> callBackListItem) {
                addLink(callBackListItem, callBackListItem.getModelObject());
            }
        };
        myContainer.add(listView);
    }


    //TODO very awfull to be refactored
    private void addLink(ListItem<CallBack> callBackListItem, final CallBack buttonCallBack) {
        if ("Export wiki".equals(buttonCallBack.getLabel())) {
            AjaxLink link = new AjaxLink("imageLink") {
                @Override
                protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
                    getResponse().write(buttonCallBack.getLabel());
                    super.onComponentTagBody(markupStream, openTag);
                }


                @Override
                public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                    buttonCallBack.onClickCallBack(ajaxRequestTarget);
                }
            };
            link.add(new ExternalImage("imageLogo", buttonCallBack.getImagePath()));
            callBackListItem.add(link);
        }
        else {
            Link link = new Link("imageLink") {
                @Override
                protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
                    getResponse().write(buttonCallBack.getLabel());
                    super.onComponentTagBody(markupStream, openTag);
                }


                @Override
                public void onClick() {
                    buttonCallBack.onClickCallBack(null);
                }
            };
            link.add(new ExternalImage("imageLogo", buttonCallBack.getImagePath()));
            callBackListItem.add(link);
        }
    }
}

