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
public class CurrentMonthBrinFilterTest {

    private CurrentMonthBrinFilter filter;
    private Brin brinUn;
    DateTime today;


    @Before
    public void setup() throws Exception {
        today = new DateTime();
        filter = new CurrentMonthBrinFilter("mybrin", "toto");
        brinUn = new Brin("brinUn");
        brinUn.setStatus(Status.unblocked);
    }


    @Test
    public void test_filterOnCreationDate() throws Exception {
        Assert.assertTrue(filter.doFilter(brinUn));

        brinUn.setCreationDate(today.withDayOfMonth(1).toDate());
        Assert.assertTrue(filter.doFilter(brinUn));

        brinUn.setCreationDate(today.withDayOfMonth(1).minusDays(1).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setCreationDate(today.minusMonths(2).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));
    }


    @Test
    public void test_filterOnUnblockingDate() throws Exception {
        brinUn.setCreationDate(today.minusMonths(2).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setUnBlockingDate(today.withDayOfMonth(2).toDate());
        Assert.assertTrue(filter.doFilter(brinUn));
    }


    @Test
    public void test_filterCurrentStatusAlwaysFilteredOthersNo() throws Exception {
        brinUn.setCreationDate(today.minusMonths(2).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setStatus(Status.current);
        Assert.assertTrue(filter.doFilter(brinUn));

        brinUn.setStatus(Status.toEradicate);
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setStatus(Status.eradicated);
        Assert.assertFalse(filter.doFilter(brinUn));
    }
}
