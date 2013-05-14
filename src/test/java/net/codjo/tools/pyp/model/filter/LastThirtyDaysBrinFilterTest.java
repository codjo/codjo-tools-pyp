package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 *
 */
public class LastThirtyDaysBrinFilterTest {
    private LastThirtyDaysBrinFilter filter;
    private Brin brinUn;
    DateTime today;


    @Before
    public void setup() throws Exception {
        today = new DateTime();
        filter = new LastThirtyDaysBrinFilter("mybrin", "toto");
        brinUn = new Brin("brinUn");
        brinUn.setStatus(Status.unblocked);
    }


    @Test
    public void test_filterOnCreationDate() throws Exception {
        Assert.assertTrue(filter.doFilter(brinUn));

        brinUn.setCreationDate(today.minusDays(10).toDate());
        Assert.assertTrue(filter.doFilter(brinUn));

        brinUn.setCreationDate(today.plusDays(2).toDate());
        Assert.assertTrue(filter.doFilter(brinUn));

        DateTime slidingMonth = today.minusDays(30).withMillisOfDay(0);
        brinUn.setCreationDate(slidingMonth.toDate());
        Assert.assertTrue(filter.doFilter(brinUn));

        brinUn.setCreationDate(slidingMonth.minus(1).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setCreationDate(today.minusDays(32).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));
    }


    @Test
    public void test_filterOnUnblockingDate() throws Exception {
        brinUn.setCreationDate(today.minusDays(32).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setUnBlockingDate(today.plusDays(2).toDate());
        Assert.assertTrue(filter.doFilter(brinUn));

        DateTime weekLimitDate = today.minusDays(30).withMillisOfDay(0);
        brinUn.setUnBlockingDate(weekLimitDate.toDate());
        Assert.assertTrue(filter.doFilter(brinUn));

        brinUn.setUnBlockingDate(weekLimitDate.minus(1).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setUnBlockingDate(today.minusDays(32).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));
    }


    @Test
    public void test_filterCurrentStatusAlwaysFilteredOthersNo() throws Exception {
        Assert.assertTrue(filter.doFilter(brinUn));
        brinUn.setCreationDate(today.minusDays(32).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setStatus(Status.toEradicate);
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setStatus(Status.eradicated);
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setStatus(Status.current);
        Assert.assertTrue(filter.doFilter(brinUn));
    }
}
