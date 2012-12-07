package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;
/**
 *
 */
public class DefaultBrinFilter implements BrinFilter {
    private String brinId;
    private String displayLabel;


    public DefaultBrinFilter(String brinId, String displayLabel) {
        this.brinId = brinId;
        this.displayLabel = displayLabel;
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
}
