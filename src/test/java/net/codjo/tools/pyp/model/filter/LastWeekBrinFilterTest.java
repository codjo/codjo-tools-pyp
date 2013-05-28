package net.codjo.tools.pyp.model.filter;
import org.joda.time.DateTime;
import org.junit.Test;

import static net.codjo.test.common.matcher.JUnitMatchers.*;
/**
 *
 */
public class LastWeekBrinFilterTest {
    @Test
    public void test_getFirstAcceptableDate() throws Exception {
        DateTime today = new DateTime();

        DateTime firstAcceptableDate = new LastWeekBrinFilter("mybrin", "toto", today).getFirstAcceptableDate();

        assertThat(today.isAfter(firstAcceptableDate), is(true));
        assertThat(today.minusDays(10).isBefore(firstAcceptableDate), is(true));
        assertThat(today.plusDays(2).isAfter(firstAcceptableDate), is(true));
        assertThat(today.minusDays(32).isBefore(firstAcceptableDate), is(true));

        DateTime weekLimitDate = today.minusDays(7).withMillisOfDay(0);
        assertThat(weekLimitDate.isAfter(firstAcceptableDate), is(true));
        assertThat(weekLimitDate.minus(1).isEqual(firstAcceptableDate), is(true));
        assertThat(weekLimitDate.minus(2).isBefore(firstAcceptableDate), is(true));
    }
}
