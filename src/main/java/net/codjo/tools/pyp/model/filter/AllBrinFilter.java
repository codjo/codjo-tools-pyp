package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;
import org.joda.time.DateTime;
/**
 *
 */
public class AllBrinFilter extends AbstractBrinFilter {

    public AllBrinFilter(String brinId, String displayLabel, DateTime from) {
        super(brinId, displayLabel, from);
    }


    @Override
    public boolean doFilter(Brin brin) {
        return true;
    }


    @Override
    public DateTime getFirstAcceptableDate() {
        return null;
    }
}
