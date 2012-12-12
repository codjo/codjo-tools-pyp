package net.codjo.tools.pyp.pages;

import java.util.Arrays;
import net.codjo.tools.pyp.ExternalImage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

public class RightPanel extends Panel {
    private static final String IMAGE_LINK = "imageLink";


    public RightPanel(String id, final CallBack... buttonCallBack) {
        super(id);
        WebMarkupContainer myContainer = new WebMarkupContainer("myContainer");
        add(myContainer);

        ListView<CallBack> listView = new ListView<CallBack>("menuList", Arrays.asList(buttonCallBack)) {
            @Override
            protected void populateItem(ListItem<CallBack> callBackListItem) {
                addLink(callBackListItem, callBackListItem.getModelObject());
            }
        };
        myContainer.add(listView);
    }


    private void addLink(ListItem<CallBack> callBackListItem, final CallBack buttonCallBack) {
        AjaxLink link = new AjaxLink(IMAGE_LINK) {
            @Override
            protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
                getResponse().write(buttonCallBack.getLabel());
                super.onComponentTagBody(markupStream, openTag);
            }


            @SuppressWarnings({"unchecked"})
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                buttonCallBack.onClickCallBack(ajaxRequestTarget);
            }
        };
        link.add(new ExternalImage("imageLogo", buttonCallBack.getImagePath()));
        callBackListItem.add(link);
    }
}

