package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.joda.time.DateTime;

public class CurrentYearBrinFilter extends DefaultBrinFilter{

    public CurrentYearBrinFilter(String brinId, String displayLabel) {
        super(brinId, displayLabel);
    }


    @Override
    public boolean doFilter(Brin brin) {
        if (Status.current.equals(brin.getStatus())) {
            return true;
        }
        DateTime creationDateTime = new DateTime(brin.getCreationDate());
        DateTime firstDayOfMonth = new DateTime().withDayOfYear(1).withHourOfDay(0);

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
