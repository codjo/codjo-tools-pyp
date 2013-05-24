package net.codjo.tools.pyp.model.filter;
import org.joda.time.DateTime;

public class LastWeekBrinFilter  extends AbstractBrinFilter  {

    public LastWeekBrinFilter(String brinId, String displayLabel, DateTime from) {
        super(brinId, displayLabel, from);
    }

    public DateTime getFirstAcceptableDate() {
        return getFrom().minusDays(7).withMillisOfDay(0).minus(1);
    }
}
