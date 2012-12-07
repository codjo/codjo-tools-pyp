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
public class CurrentYearBrinFilterTest {

    private CurrentYearBrinFilter filter;
    private Brin brinUn;


    @Before
    public void setup() throws Exception {
        filter = new CurrentYearBrinFilter("mybrin", "toto");
        brinUn = new Brin("brinUn");
        brinUn.setStatus(Status.unblocked);
    }


    @Test
    public void test_filterOnCreationDate() throws Exception {
        Assert.assertTrue(filter.doFilter(brinUn));

        brinUn.setCreationDate(new DateTime().minusYears(1).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setCreationDate(new DateTime().withDayOfYear(1).toDate());
        Assert.assertTrue(filter.doFilter(brinUn));
    }


    @Test
    public void test_filterOnUnblockingDate() throws Exception {
        brinUn.setCreationDate(new DateTime().minusYears(1).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setUnBlockingDate(new DateTime().withDayOfYear(2).toDate());
        Assert.assertTrue(filter.doFilter(brinUn));
    }


    @Test
    public void test_filterCurrentStatusAlwaysFiltered() throws Exception {
        Assert.assertTrue(filter.doFilter(brinUn));
        brinUn.setCreationDate(new DateTime().minusYears(1).toDate());
        Assert.assertFalse(filter.doFilter(brinUn));

        brinUn.setStatus(Status.current);
        Assert.assertTrue(filter.doFilter(brinUn));
    }
}
