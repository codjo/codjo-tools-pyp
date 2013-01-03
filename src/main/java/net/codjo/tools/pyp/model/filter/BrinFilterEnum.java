package net.codjo.tools.pyp.model.filter;
import org.joda.time.DateTime;
/**
 * TODO Do we really need an enum to make a factory ? ;-)
 */
public enum BrinFilterEnum {
    ALL_BRIN("AllBrinsFilter"),
    LAST_WEEK("LastWeekFilter"),
    CURRENT_MONTH("CurrentMonthFilter"),
    CURRENT_YEAR("CurrentYearFilter");

    private String brinFilterId;


    private BrinFilterEnum(String filterId) {
        this.brinFilterId = filterId;
    }


    public BrinFilter get(DateTime from) {
        return get(brinFilterId, from);
    }


    public static BrinFilter get(String brinFilterId, DateTime from) {
        if ("LastWeekFilter".equals(brinFilterId)) {
            return new LastWeekBrinFilter(LAST_WEEK.brinFilterId, "D-7 brins", from);
        }
        if ("CurrentMonthFilter".equals(brinFilterId)) {
            return new CurrentMonthBrinFilter(CURRENT_MONTH.brinFilterId, "Current Month", from);
        }
        if ("CurrentYearFilter".equals(brinFilterId)) {
            return new CurrentYearBrinFilter(CURRENT_YEAR.brinFilterId, "Current Year", from);
        }

        return new AllBrinFilter(ALL_BRIN.brinFilterId, "All brins", from);
    }

}
