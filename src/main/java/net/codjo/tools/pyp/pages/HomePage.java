package net.codjo.tools.pyp.pages;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.codjo.tools.pyp.ExternalImage;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.filter.AllBrinFilter;
import net.codjo.tools.pyp.model.filter.BrinFilter;
import net.codjo.tools.pyp.model.filter.BrinFilterEnum;
import net.codjo.tools.pyp.services.BrinService;
import net.codjo.tools.pyp.services.CsvService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.StringResourceStream;
/**
 *
 */
public class HomePage extends RootPage {
    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    public HomePage(final PageParameters pageParameters) {
        if (pageParameters.containsKey("brinFilter")) {
            brinFilter = BrinFilterEnum.get(pageParameters.getString("brinFilter"));
        }
        else {
            brinFilter = BrinFilterForm.DEFAULT_BRIN_FILTER;
        }
        buildPage();
    }


    public HomePage(BrinFilter brinFilter) {
        super(brinFilter);
        buildPage();
    }


    private void buildPage() {
        BrinListView dataView = new BrinListView("brinList");
        WebMarkupContainer brinListContainer = new WebMarkupContainer("brinListContainer");
        brinListContainer.setOutputMarkupId(true);
        brinListContainer.add(dataView);
        add(brinListContainer);
//        add(new CloseOnEscBehavior(addApplicationPanel, closeButtonCallback, "document", "keyup"));
    }


    @Override
    protected void initRightPanel(String id) {
        final ModalWindow addApplicationPanel = createWikiExportPanel();
        add(addApplicationPanel);

        CallBack buttonCallBack = new CallBack<Brin>() {
            public void onClickCallBack(Brin brin) {
                responseWithEdit(brin);
            }


            public String getLabel() {
                return "Add new BRIN";
            }


            public String getImagePath() {
                return "images/brin_form_add.png";
            }
        };

        CallBack exportCallBack = new CallBack<Brin>() {
            public void onClickCallBack(Brin brin) {
                //TODO On pourrait creer un DownloadLink pour encapsuler ce comportement
                StringBuilder content = CsvService.export(BrinService.getBrinService(HomePage.this).getAllBrins(
                      getBrinFilter()));
                StringResourceStream stream = new StringResourceStream(content.toString(), "text/csv");
                ResourceStreamRequestTarget requestTarget = new ResourceStreamRequestTarget(stream);
                requestTarget.setFileName("ExportBrin.csv");
                getRequestCycle().setRequestTarget(requestTarget);
            }


            public String getLabel() {
                return "Export BRINs (csv)";
            }


            public String getImagePath() {
                return "images/export.png";
            }
        };
        CallBack wikiExportCallBack = new AjaxCallBack() {
            public void onClickCallBack(Object brin) {
                addApplicationPanel.show((AjaxRequestTarget) brin);
            }

            public void onClickCallBack(Object brin, AjaxRequestTarget target) {
                addApplicationPanel.show(target);
            }

            public String getLabel() {
                return "Export wiki";
            }

            public String getImagePath() {
                return "images/export.png";
            }
        };
        add(new RightPanel(id, buttonCallBack, exportCallBack,wikiExportCallBack));
    }


    @Override
    protected void initLeftPanel(String id) {
        CallBack brinFilterCallBack = new CallBack<AllBrinFilter>() {
            public void onClickCallBack(AllBrinFilter brinFilter) {
                setBrinFilter(brinFilter);
            }


            public String getLabel() {
                return "brinFilterCallBack";
            }


            public String getImagePath() {
                return null;
            }
        };
        add(new LeftPanel(id,brinFilter, brinFilterCallBack));
    }


    //TODO copier coller from Magic
    private ModalWindow createWikiExportPanel() {
        final ModalWindow wikiExportWindow = new ModalWindow("wikiExportPanel");

        wikiExportWindow.setTitle("Wiki export");
        wikiExportWindow.setInitialWidth(700);
        wikiExportWindow.setMinimalWidth(700);
        wikiExportWindow.setInitialHeight(435);
        wikiExportWindow.setOutputMarkupId(true);

        final WikiExportPanel wikiExportPanel
                = new WikiExportPanel(wikiExportWindow.getContentId(),
                null, brinFilter);

        wikiExportWindow.setContent(wikiExportPanel);
        wikiExportPanel.setOutputMarkupId(true);

        ModalWindow.CloseButtonCallback closeButtonCallback = new ModalWindow.CloseButtonCallback() {
            public boolean onCloseButtonClicked(AjaxRequestTarget target) {
                // do onClose stuff
                return true;
            }
        };
        wikiExportWindow.setCloseButtonCallback(closeButtonCallback);
        return wikiExportWindow;
    }



    private void responseWithEdit(Brin brin) {
        setResponsePage(new BrinEditPage(brin, brinFilter));
    }


    private String formatDate(Date dateToFormat) {
        if (dateToFormat == null) {
            return "";
        }
        return format.format(dateToFormat);
    }


    public BrinFilter getBrinFilter() {
        return brinFilter;
    }


    public void setBrinFilter(AllBrinFilter brinFilter) {
        this.brinFilter = brinFilter;
    }


    public interface CallBack<T> extends Serializable {
        void onClickCallBack(T brin);


        String getLabel();


        String getImagePath();
    }

    public interface AjaxCallBack<T> extends CallBack<T> {
        void onClickCallBack(T brin, AjaxRequestTarget target);


        String getLabel();


        String getImagePath();
    }

    private class BrinListView extends RefreshingView<Brin> {

        public BrinListView(String id) {
            super(id);
        }


        @Override
        protected Iterator<IModel<Brin>> getItemModels() {
            List<Brin> filteredList = BrinService.getBrinService(this).getAllBrins(getBrinFilter());
            return new ModelIteratorAdapter<Brin>(filteredList.iterator()) {
                @Override
                protected Model<Brin> model(Brin object) {
                    return new Model<Brin>(object);
                }
            };
        }


        @Override
        protected void populateItem(Item<Brin> listItem) {
            final Brin brin = listItem.getModelObject();
            listItem.add(new Label("title", brin.getTitle()));
            listItem.add(new Label("creationDate", formatDate(brin.getCreationDate())));
            listItem.add(new Label("status", brin.getStatus().toString()));

            Link editionLink = new Link("editBrin") {
                @Override
                public void onClick() {
                    responseWithEdit(brin);
                }
            };
            editionLink.add(new ExternalImage("editBrinLogo", "images/brin_form_edit.png"));
            listItem.add(editionLink);
            listItem.add(new AjaxEventBehavior("ondblclick") {
                @Override
                protected void onEvent(AjaxRequestTarget target) {
                    responseWithEdit(brin);
                }
            });
        }
    }
}
