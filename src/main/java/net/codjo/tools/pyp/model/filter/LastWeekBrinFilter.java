package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import net.codjo.tools.pyp.model.Status;

public class LastWeekBrinFilter extends BrinFilter implements Serializable {

    public LastWeekBrinFilter(String brinId, String displayLabel) {
        super(brinId, displayLabel);
    }

    @Override
    public boolean doFilter(Brin brin) {
        if (Status.current.equals(brin.getStatus())){
            return true;
        }
        Date creationDate = brin.getCreationDate();
        Date lastWeekDate = shiftDate(Calendar.getInstance().getTime(), -8);
        boolean creationDateInLastWeek = creationDate.compareTo(lastWeekDate) > 0;
        Date unBlockingDate = brin.getUnBlockingDate();
        boolean unblockingDateInLastWeek = unBlockingDate!=null && unBlockingDate.compareTo(lastWeekDate) > 0;
        return creationDateInLastWeek || unblockingDateInLastWeek;
    }


    public static Date shiftDate(Date dateToShift, int nbOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToShift);
        calendar.add(Calendar.DAY_OF_MONTH, nbOfDays);
        return calendar.getTime();
    }
}
