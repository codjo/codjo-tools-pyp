package net.codjo.tools.pyp.pages;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import net.codjo.tools.pyp.pages.HomePage.CallBack;
import net.codjo.tools.pyp.services.BrinService;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
/**
 *
 */
public class LeftPanel extends Panel {
    public LeftPanel(String id, final CallBack... buttonCallBack) {
        super(id);
        add(brinFilterForm(buttonCallBack[0]));

        WebMarkupContainer myContainer = new WebMarkupContainer("summaryPanel");
        myContainer.add(new StatusView("infoList"));
        myContainer.setOutputMarkupId(true);
        add(myContainer);
    }

    private Component brinFilterForm(CallBack brinFilter) {
        return new BrinFilterForm("filterForm", brinFilter).setVisible(brinFilter != null);
    }

    private class StatusView extends RefreshingView<Status> {
        private Map<Status, Integer> statusIntegerMap;


        public StatusView(String id) {
            super(id);
        }


        @Override
        protected Iterator<IModel<Status>> getItemModels() {
            BrinService brinService = BrinService.getBrinService(this);
            //TODO pas beau le ((HomePage)getPage()).getBrinFilter() !!!
            List<Brin> allBrins = brinService.getAllBrins(((HomePage)getPage()).getBrinFilter());
            statusIntegerMap = brinService.calculateBrinNumber(allBrins);

            List<Status> filteredList = Arrays.asList(Status.values());
            return new ModelIteratorAdapter<Status>(filteredList.iterator()) {
                @Override
                protected Model<Status> model(Status object) {
                    return new Model<Status>(object);
                }
            };
        }


        @Override
        protected void populateItem(Item<Status> statusListItem) {
            Status status = statusListItem.getModelObject();
            statusListItem.add(new Label("statusLabel", status.toString()));
            statusListItem.add(new Label("nbBrin", statusIntegerMap.get(status).toString()));
        }
    }
}
