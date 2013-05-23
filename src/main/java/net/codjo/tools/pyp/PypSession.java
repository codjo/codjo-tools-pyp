package net.codjo.tools.pyp;
import net.codjo.tools.pyp.model.filter.BrinFilter;
import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

public class PypSession extends WebSession {
    private final static String BRIN_FILTER_KEY = "brinFilter";


    public PypSession(Request request) {
        super(request);
    }


    public BrinFilter getBrinFilter() {
        return (BrinFilter)getAttribute(BRIN_FILTER_KEY);
    }


    public void setBrinFilter(BrinFilter brinFilter) {
        setAttribute(BRIN_FILTER_KEY, brinFilter);
    }
}
