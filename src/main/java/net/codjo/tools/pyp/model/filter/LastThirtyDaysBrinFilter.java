package net.codjo.tools.pyp.model.filter;
import org.joda.time.DateTime;
/**
 *
 */
public class LastThirtyDaysBrinFilter  extends AbstractBrinFilter {

    public LastThirtyDaysBrinFilter(String brinId, String displayLabel, DateTime from) {
        super(brinId, displayLabel, from);
    }


    public DateTime getFirstAcceptableDate() {
        return getFrom().minusDays(30).withMillisOfDay(0).minus(1);
    }
}
