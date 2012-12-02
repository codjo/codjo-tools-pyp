package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;

import java.io.Serializable;
/**
 *
 */
public class BrinFilter implements Serializable {
    private String brinId;
    private String displayLabel;


    public BrinFilter(String brinId, String displayLabel) {
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
