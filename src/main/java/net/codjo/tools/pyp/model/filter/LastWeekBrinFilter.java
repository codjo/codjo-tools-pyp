package net.codjo.tools.pyp.model.filter;
import java.util.Calendar;
import java.util.Date;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.joda.time.DateTime;

public class LastWeekBrinFilter extends AllBrinFilter {

    public LastWeekBrinFilter(String brinId, String displayLabel) {
        this(brinId, displayLabel, new DateTime());
    }


    public LastWeekBrinFilter(String brinId, String displayLabel, DateTime from) {
        super(brinId, displayLabel, from);
    }


    @Override
    public boolean doFilter(Brin brin) {
        if (Status.current.equals(brin.getStatus())) {
            return true;
        }
        DateTime creationDateTime = new DateTime(brin.getCreationDate());
        DateTime lastWeekDate = getFrom().minusDays(7).withMillisOfDay(0).minus(1);
        boolean creationDateInLastWeek = creationDateTime.isAfter(lastWeekDate.toInstant());

        DateTime unBlockingDate;
        if (brin.getUnBlockingDate() == null) {
            unBlockingDate = null;
        }
        else {
            unBlockingDate = new DateTime(brin.getUnBlockingDate());
        }
        boolean unblockingDateInLastWeek = unBlockingDate != null && unBlockingDate.isAfter(lastWeekDate.toInstant());

        return creationDateInLastWeek || unblockingDateInLastWeek;
    }


    public static Date shiftDate(Date dateToShift, int nbOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToShift);
        calendar.add(Calendar.DAY_OF_MONTH, nbOfDays);
        return calendar.getTime();
    }
}
