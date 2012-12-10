package net.codjo.tools.pyp.pages;
import java.util.ArrayList;
import java.util.List;
import net.codjo.tools.pyp.model.filter.BrinFilter;
import net.codjo.tools.pyp.model.filter.CurrentMonthBrinFilter;
import net.codjo.tools.pyp.model.filter.CurrentYearBrinFilter;
import net.codjo.tools.pyp.model.filter.LastWeekBrinFilter;
import net.codjo.tools.pyp.pages.HomePage.CallBack;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
/**
 * TODO : put generified class in codjo-wicket (copied from LanguagePanel in magic)
 */
public class BrinFilterForm extends Form {
    private static final Logger LOG = Logger.getLogger(BrinFilterForm.class);


    public BrinFilterForm(String id, final CallBack<BrinFilter> callBack) {
        super(id);
        ChoiceRenderer<BrinFilter> choiceRenderer = new ChoiceRenderer<BrinFilter>("displayLabel", "brinId") {
            @Override
            public Object getDisplayValue(BrinFilter object) {
                return object.getDisplayLabel();
            }
        };
        List<BrinFilter> brinFilters = new ArrayList<BrinFilter>();
        brinFilters.add(new LastWeekBrinFilter("LastWeekFilter", "D-7 brins"));
        brinFilters.add(new CurrentMonthBrinFilter("CurrentMonthFilter", "Current Month"));
        brinFilters.add(new CurrentYearBrinFilter("CurrentYearFilter", "Current Year"));
        brinFilters.add(new BrinFilter("AllBrinsFilter", "All brins"));

        DropDownChoice choice = new DropDownChoice<BrinFilter>("brinFilters",
                                                               new Model<BrinFilter>(new BrinFilter("AllBrinsFilter",
                                                                                                    "NotUsed")),
                                                               brinFilters,
                                                               choiceRenderer);
        choice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                BrinFilter brinFilter = (BrinFilter)getFormComponent().getModelObject();
                callBack.onClickCallBack(brinFilter);
                addComponentToTarget("brinListContainer", target);
                addComponentToTarget("leftPanel:summaryPanel", target);
            }
        });
        add(choice);
    }


    private void addComponentToTarget(String componentId, AjaxRequestTarget target) {
        Component component = getPage().get(componentId);
        if (component != null) {
            if (component.getOutputMarkupId()) {
                target.addComponent(component);
            }
            else {
                LOG.warn("Component " + componentId + " outputMarkup is set to false: it has not been refreshed");
            }
        }
    }
}
