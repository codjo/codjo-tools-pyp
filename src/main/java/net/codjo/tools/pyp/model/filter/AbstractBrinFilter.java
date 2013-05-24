package net.codjo.tools.pyp.model.filter;
import java.util.Date;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.joda.time.DateTime;
/**
 *
 */
public abstract class AbstractBrinFilter implements BrinFilter {

    protected String brinId;
    protected String displayLabel;
    protected DateTime from;


    public AbstractBrinFilter(String brinId, String displayLabel, DateTime from) {
        this.brinId = brinId;
        this.from = from;
        this.displayLabel = displayLabel;
    }


    public boolean doFilter(Brin brin) {
        if (Status.current.equals(brin.getStatus())) {
            return true;
        }
        DateTime creationDateTime = new DateTime(brin.getCreationDate());
        DateTime firstAcceptableDate = getFirstAcceptableDate();

        DateTime unBlockingDate = convertDateToDateTime(brin.getUnBlockingDate());
        DateTime eradicationDate = convertDateToDateTime(brin.getEradicationDate());
        return creationDateTime.isAfter(firstAcceptableDate) || (unBlockingDate != null && unBlockingDate.isAfter(
              firstAcceptableDate) || (eradicationDate != null && eradicationDate.isAfter(
              firstAcceptableDate)));
    }


    private DateTime convertDateToDateTime(final Date date) {
        DateTime dateTime;
        if (date == null) {
            dateTime = null;
        }
        else {
            dateTime = new DateTime(date);
        }
        return dateTime;
    }


    public abstract DateTime getFirstAcceptableDate();


    public String getDisplayLabel() {
        if (displayLabel == null) {
            return brinId;
        }
        return displayLabel;
    }


    public String getBrinId() {
        return brinId;
    }


    public DateTime getFrom() {
        return from;
    }
}
