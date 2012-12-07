package net.codjo.tools.pyp.pages;
import java.util.ArrayList;
import java.util.List;
import net.codjo.tools.pyp.model.filter.DefaultBrinFilter;
import net.codjo.tools.pyp.model.filter.BrinFilterEnum;
import net.codjo.tools.pyp.model.filter.BrinFilter;
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
    static final BrinFilter DEFAULT_BRIN_FILTER = BrinFilterEnum.ALL_BRIN.get();


    public BrinFilterForm(String id, BrinFilter filter, final CallBack<BrinFilter> callBack) {
        super(id);
        ChoiceRenderer<BrinFilter> choiceRenderer = new ChoiceRenderer<BrinFilter>("displayLabel", "brinId") {
            public Object getDisplayValue(DefaultBrinFilter object) {
                return object.getDisplayLabel();
            }
        };
        List<BrinFilter> brinFilters = new ArrayList<BrinFilter>();
        brinFilters.add(BrinFilterEnum.LAST_WEEK.get());
        brinFilters.add(BrinFilterEnum.CURRENT_MONTH.get());
        brinFilters.add(BrinFilterEnum.CURRENT_YEAR.get());
        brinFilters.add(DEFAULT_BRIN_FILTER);

        System.out.println("filter = " + filter);
        BrinFilter defaultBrinFilter = DEFAULT_BRIN_FILTER;
        if (filter==null){
            defaultBrinFilter = DEFAULT_BRIN_FILTER;    
        }
        DropDownChoice choice = new DropDownChoice<BrinFilter>("brinFilters",
                                                               new Model<BrinFilter>(defaultBrinFilter),
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
