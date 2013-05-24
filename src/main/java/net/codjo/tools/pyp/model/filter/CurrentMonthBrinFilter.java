package net.codjo.tools.pyp.model.filter;
import org.joda.time.DateTime;

public class CurrentMonthBrinFilter extends AbstractBrinFilter {

    public CurrentMonthBrinFilter(String brinId, String displayLabel, DateTime from) {
        super(brinId, displayLabel, from);
    }


    public DateTime getFirstAcceptableDate() {
        return getFrom().withDayOfMonth(1).withMillisOfDay(0).minus(1);
    }
}
