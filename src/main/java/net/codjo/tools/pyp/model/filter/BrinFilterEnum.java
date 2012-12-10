package net.codjo.tools.pyp.model.filter;
/**
 * TODO Do we really need an enum to make a factory ? ;-)
 */
public enum BrinFilterEnum {
    ALL_BRIN("AllBrinsFilter"),
    LAST_WEEK("LastWeekFilter"),
    CURRENT_MONTH("CurrentMonthFilter"),
    CURRENT_YEAR("CurrentYearFilter");

    private String brinId;


    private BrinFilterEnum(String filterId) {
        this.brinId = filterId;
    }


    public String getBrinId() {
        return brinId;
    }


    public static BrinFilter get(String brinId) {
        final AllBrinFilter allBrinFilter = new AllBrinFilter(ALL_BRIN.getBrinId(), "All brins");
        if ("AllBrinsFilter".equals(brinId)) {
            return allBrinFilter;
        }
        if ("LastWeekFilter".equals(brinId)) {
            return new LastWeekBrinFilter(LAST_WEEK.getBrinId(), "D-7 brins");
        }
        if ("CurrentMonthFilter".equals(brinId)) {
            return new CurrentMonthBrinFilter(CURRENT_MONTH.getBrinId(), "Current Month");
        }
        if ("CurrentYearFilter".equals(brinId)) {
            return new CurrentYearBrinFilter(CURRENT_YEAR.getBrinId(), "Current Year");
        }
        return allBrinFilter;
    }


    public BrinFilter get() {
        return get(this.getBrinId());
    }

}
