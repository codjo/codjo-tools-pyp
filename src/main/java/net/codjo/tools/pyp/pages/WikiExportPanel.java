package net.codjo.tools.pyp.pages;

import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.filter.BrinFilter;
import net.codjo.tools.pyp.services.BrinService;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.protocol.http.WebRequest;

import java.util.List;

public class WikiExportPanel extends Panel {
    public WikiExportPanel(String id, HomePage.CallBack newApplicationCallBack, BrinFilter brinFilter) {
        super(id);

//TODO pas fait au bon endroit, renvoie toujours la meme liste
        List<Brin> filteredList = BrinService.getBrinService(this).getAllBrins(brinFilter);
        StringBuilder builder = new StringBuilder();
        for (Brin brin : filteredList) {
            builder.append("* ").append(brin.getStatus()).append(" ")
                    .append("[").append(brin.getTitle()).append(" | ")
                    .append(getApplicationUrl()).append("?id=")
                    .append(brin.getUuid()).append("\n");
        }

        TextArea<String> wikiContent = new TextArea<String>("wikiContent", new Model<String>(builder.toString()));
        add(wikiContent);
    }


    private String getApplicationUrl() {
        String result = RequestUtils.toAbsolutePath(RequestCycle.get().getRequest().getRelativePathPrefixToWicketHandler())+"/edit.html";
        return result;
    }
}
