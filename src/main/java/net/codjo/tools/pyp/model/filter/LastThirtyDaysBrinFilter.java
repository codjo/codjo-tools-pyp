package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.joda.time.DateTime;
/**
 *
 */
public class LastThirtyDaysBrinFilter extends AllBrinFilter {

    public LastThirtyDaysBrinFilter(String brinId, String displayLabel) {
        this(brinId, displayLabel, new DateTime());
    }


    public LastThirtyDaysBrinFilter(String brinId, String displayLabel, DateTime from) {
        super(brinId, displayLabel, from);
    }


    @Override
    public boolean doFilter(Brin brin) {
        if (Status.current.equals(brin.getStatus())) {
            return true;
        }
        DateTime creationDateTime = new DateTime(brin.getCreationDate());
        DateTime lastSlidingMonthDate = getFrom().minusDays(30).withMillisOfDay(0).minus(1);
        boolean creationDateInLastThirtyDays = creationDateTime.isAfter(lastSlidingMonthDate.toInstant());

        DateTime unBlockingDate;
        if (brin.getUnBlockingDate() == null) {
            unBlockingDate = null;
        }
        else {
            unBlockingDate = new DateTime(brin.getUnBlockingDate());
        }
        boolean unblockingDateInLastWeek = unBlockingDate != null && unBlockingDate.isAfter(lastSlidingMonthDate.toInstant());

        return creationDateInLastThirtyDays || unblockingDateInLastWeek;
    }
}
