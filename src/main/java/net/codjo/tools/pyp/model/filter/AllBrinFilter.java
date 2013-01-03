package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;
import org.joda.time.DateTime;
/**
 *
 */
public class AllBrinFilter implements BrinFilter {
    private String brinId;
    private String displayLabel;
    private DateTime from;


    public AllBrinFilter(String brinId, String displayLabel, DateTime from) {
        this.brinId = brinId;
        this.displayLabel = displayLabel;
        this.from = from;
    }


    public String getDisplayLabel() {
        if (displayLabel == null) {
            return brinId;
        }
        return displayLabel;
    }


    public String getBrinId() {
        return brinId;
    }


    public boolean doFilter(Brin brin) {
        return true;
    }


    public DateTime getFrom() {
        return from;
    }
}
