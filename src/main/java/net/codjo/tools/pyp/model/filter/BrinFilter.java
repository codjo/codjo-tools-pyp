package net.codjo.tools.pyp.model.filter;
import java.io.Serializable;
import net.codjo.tools.pyp.model.Brin;
/**
 *
 */
public interface BrinFilter extends Serializable {
    public boolean doFilter(Brin brin);


    public String getBrinId();


    public String getDisplayLabel();
}
