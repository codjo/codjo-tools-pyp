package net.codjo.tools.pyp.model.filter;
import org.joda.time.DateTime;
import org.junit.Test;

import static net.codjo.test.common.matcher.JUnitMatchers.*;
/**
 *
 */
public class CurrentMonthBrinFilterTest {

    @Test
    public void test_getFirstAcceptableDate() throws Exception {
        DateTime today = new DateTime();

        DateTime firstAcceptableDate = new CurrentMonthBrinFilter("mybrin", "toto", today).getFirstAcceptableDate();

        assertThat(firstAcceptableDate.isBefore(today.withDayOfMonth(1)), is(true));
        assertThat(firstAcceptableDate.isAfter(today.withDayOfMonth(1).minusDays(1)), is(true));
        assertThat(firstAcceptableDate.isBefore(today.withDayOfMonth(1).withMillisOfDay(0)), is(true));
        assertThat(firstAcceptableDate.isAfter(today.minusMonths(2)), is(true));
    }
}
