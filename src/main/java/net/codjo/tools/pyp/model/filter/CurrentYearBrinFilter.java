package net.codjo.tools.pyp.model.filter;
import org.joda.time.DateTime;

public class CurrentYearBrinFilter  extends AbstractBrinFilter  {


    public CurrentYearBrinFilter(String brinId, String displayLabel, DateTime from) {
        super(brinId, displayLabel, from);
    }


    public DateTime getFirstAcceptableDate() {
        return getFrom().withDayOfYear(1).withMillisOfDay(0).minus(1);
    }
}
