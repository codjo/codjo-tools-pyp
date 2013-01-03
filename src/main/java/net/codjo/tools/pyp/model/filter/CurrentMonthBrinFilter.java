package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.joda.time.DateTime;

public class CurrentMonthBrinFilter extends AllBrinFilter {

    public CurrentMonthBrinFilter(String brinId, String displayLabel) {
        this(brinId, displayLabel, new DateTime());
    }


    public CurrentMonthBrinFilter(String brinId, String displayLabel, DateTime from) {
        super(brinId, displayLabel, from);
    }


    @Override
    public boolean doFilter(Brin brin) {
        if (Status.current.equals(brin.getStatus())) {
            return true;
        }
        DateTime creationDateTime = new DateTime(brin.getCreationDate());
        DateTime firstDayOfMonth = getFrom().withDayOfMonth(1).withMillisOfDay(0).minus(1);

        DateTime unBlockingDate;
        if (brin.getUnBlockingDate() == null) {
            unBlockingDate = null;
        }
        else {
            unBlockingDate = new DateTime(brin.getUnBlockingDate());
        }
        return creationDateTime.isAfter(firstDayOfMonth) || (unBlockingDate != null && unBlockingDate.isAfter(
              firstDayOfMonth));
    }
}
