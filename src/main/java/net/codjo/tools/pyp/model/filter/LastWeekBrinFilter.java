package net.codjo.tools.pyp.model.filter;
import java.util.Calendar;
import java.util.Date;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.joda.time.DateTime;

public class LastWeekBrinFilter extends AllBrinFilter {

    public LastWeekBrinFilter(String brinId, String displayLabel) {
        super(brinId, displayLabel);
    }


    @Override
    public boolean doFilter(Brin brin) {
        if (Status.current.equals(brin.getStatus())) {
            return true;
        }
        DateTime creationDateTime = new DateTime(brin.getCreationDate());
        DateTime lastWeekDate = new DateTime().minusDays(7).withHourOfDay(0).withMinuteOfHour(0);
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