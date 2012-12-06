package net.codjo.tools.pyp.model.filter;
import java.util.Calendar;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 *
 */
public class LastWeekBrinFilterTest {
    private LastWeekBrinFilter filter;
    private Brin brinUn;


    @Before
    public void setup() throws Exception {
        filter = new LastWeekBrinFilter("mybrin", "toto");
        brinUn = new Brin("brinUn");
        brinUn.setStatus(Status.unblocked);
    }


    @Test
    public void test_filterOnCreationDate() throws Exception {
        Assert.assertTrue(filter.doFilter(brinUn));

        brinUn.setCreationDate(LastWeekBrinFilter.shiftDate(Calendar.getInstance().getTime(), -10));
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setCreationDate(LastWeekBrinFilter.shiftDate(Calendar.getInstance().getTime(), 2));
        Assert.assertTrue(filter.doFilter(brinUn));
    }


    @Test
    public void test_filterOnUnblockingDate() throws Exception {
        brinUn.setCreationDate(LastWeekBrinFilter.shiftDate(Calendar.getInstance().getTime(), -10));
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setUnBlockingDate(LastWeekBrinFilter.shiftDate(Calendar.getInstance().getTime(), 2));
        Assert.assertTrue(filter.doFilter(brinUn));
    }


    @Test
    public void test_filterCurrentStatusAlwaysFiltered() throws Exception {
        Assert.assertTrue(filter.doFilter(brinUn));
        brinUn.setCreationDate(LastWeekBrinFilter.shiftDate(Calendar.getInstance().getTime(), -10));
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setStatus(Status.current);
        Assert.assertTrue(filter.doFilter(brinUn));
    }
}

