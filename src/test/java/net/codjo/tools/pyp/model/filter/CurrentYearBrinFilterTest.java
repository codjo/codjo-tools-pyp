package net.codjo.tools.pyp.model.filter;
import org.joda.time.DateTime;
import org.junit.Test;

import static net.codjo.test.common.matcher.JUnitMatchers.*;
/**
 *
 */
public class CurrentYearBrinFilterTest {

    @Test
    public void test_getFirstAcceptableDate() throws Exception {
        DateTime today = new DateTime();

        DateTime firstAcceptableDate = new CurrentYearBrinFilter("mybrin", "toto", today).getFirstAcceptableDate();

        assertThat(firstAcceptableDate.isBefore(today), is(true));
        assertThat(firstAcceptableDate.isAfter(today.minusYears(1)), is(true));
        assertThat(firstAcceptableDate.isBefore(today.withDayOfYear(1)), is(true));
        assertThat(firstAcceptableDate.isBefore(today.withDayOfYear(1).withMillisOfDay(0)), is(true));
        assertThat(firstAcceptableDate.isAfter(today.withDayOfYear(1).minusDays(1)), is(true));
    }
}
