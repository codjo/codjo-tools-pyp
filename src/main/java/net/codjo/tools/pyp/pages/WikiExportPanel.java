package net.codjo.tools.pyp.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import static org.apache.wicket.protocol.http.RequestUtils.toAbsolutePath;

public class WikiExportPanel extends Panel {
    static final String WIKI_CONTENT_ID = "wikiContent";


    public WikiExportPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        TextArea<String> wikiContent = new TextArea<String>(WIKI_CONTENT_ID, new Model<String>());
        add(wikiContent);
    }


    private void addWikiContentForStatus(StringBuilder builder,
                                         Map<Status, List<Brin>> resultMap,
                                         Status status) {
        List<Brin> brinListForStatus = resultMap.get(status);
        if (brinListForStatus.size() != 0) {
            builder.append("* ").append(status.getStatus().toLowerCase()).append("\n");
            for (Brin brin : brinListForStatus) {
                builder.append("** [").append(brin.getTitle()).append(" | ")
                      .append(getApplicationUrl()).append("?id=")
                      .append(brin.getUuid()).append("]").append("\n");
            }
        }
    }


    private String getApplicationUrl() {
        return toAbsolutePath(RequestCycle.get().getRequest().getRelativePathPrefixToWicketHandler()) + "edit.html";
    }


    public void fillContent(List<Brin> brins) {
        Map<Status, List<Brin>> resultMap = new HashMap<Status, List<Brin>>();
        final Status[] values = Status.values();
        for (Status value : values) {
            resultMap.put(value, new ArrayList<Brin>());
        }

        for (Brin brin : brins) {
            resultMap.get(brin.getStatus()).add(brin);
        }

        StringBuilder builder = new StringBuilder();
        addWikiContentForStatus(builder, resultMap, Status.current);
        addWikiContentForStatus(builder, resultMap, Status.unblocked);
        addWikiContentForStatus(builder, resultMap, Status.eradicated);
        addWikiContentForStatus(builder, resultMap, Status.toEradicate);

        get(WIKI_CONTENT_ID).setDefaultModel(new Model<String>(builder.toString()));
    }
}
