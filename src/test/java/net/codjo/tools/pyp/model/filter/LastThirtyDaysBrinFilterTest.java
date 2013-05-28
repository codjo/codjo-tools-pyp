package net.codjo.tools.pyp.model.filter;
import org.joda.time.DateTime;
import org.junit.Test;

import static net.codjo.test.common.matcher.JUnitMatchers.*;
/**
 *
 */
public class LastThirtyDaysBrinFilterTest {
    @Test
    public void test_getFirstAcceptableDate() throws Exception {
        DateTime today = new DateTime();

        DateTime firstAcceptableDate = new LastThirtyDaysBrinFilter("mybrin", "toto", today).getFirstAcceptableDate();

        assertThat(firstAcceptableDate.isBefore(today), is(true));
        assertThat(firstAcceptableDate.isBefore(today.minusDays(10)), is(true));
        assertThat(firstAcceptableDate.isBefore(today.plusDays(2)), is(true));
        assertThat(firstAcceptableDate.isAfter(today.minusDays(32)), is(true));

        DateTime slidingMonth = today.minusDays(30).withMillisOfDay(0);
        assertThat(firstAcceptableDate.isBefore(slidingMonth), is(true));
        assertThat(firstAcceptableDate.isEqual(slidingMonth.minus(1)), is(true));
    }
}
