package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractBrinFilterTest {

    private BrinFilter filter;
    private Brin currentBrin;
    DateTime today;


    @Before
    public void setup() throws Exception {
        today = new DateTime();
        filter = new AbstractBrinFilter("mybrin", "toto", today) {
            @Override
            public DateTime getFirstAcceptableDate() {
                return getFrom().withDayOfMonth(1).withMillisOfDay(0).minus(1);
            }
        };

        currentBrin = new Brin("currentBrin");
        currentBrin.setStatus(Status.unblocked);
    }


    @Test
    public void test_filterOnCreationDate() throws Exception {
        Assert.assertTrue(filter.doFilter(currentBrin));

        currentBrin.setCreationDate(today.withDayOfMonth(1).toDate());
        Assert.assertTrue(filter.doFilter(currentBrin));

        currentBrin.setCreationDate(today.withDayOfMonth(1).minusDays(1).toDate());
        Assert.assertFalse(filter.doFilter(currentBrin));

        currentBrin.setCreationDate(today.minusMonths(2).toDate());
        Assert.assertFalse(filter.doFilter(currentBrin));
    }


    @Test
    public void test_filterOnUnblockingDate() throws Exception {
        currentBrin.setCreationDate(today.minusMonths(2).toDate());
        Assert.assertFalse(filter.doFilter(currentBrin));

        currentBrin.setUnBlockingDate(today.withDayOfMonth(2).toDate());
        Assert.assertTrue(filter.doFilter(currentBrin));

        currentBrin.setUnBlockingDate(today.withDayOfMonth(1).withMillisOfDay(0).toDate());
        Assert.assertTrue(filter.doFilter(currentBrin));

        currentBrin.setUnBlockingDate(today.withDayOfMonth(1).minusDays(1).toDate());
        Assert.assertFalse(filter.doFilter(currentBrin));
    }


    @Test
    public void test_filterOnEradicationDate() throws Exception {
        currentBrin.setCreationDate(today.minusMonths(2).toDate());
        Assert.assertFalse(filter.doFilter(currentBrin));

        currentBrin.setUnBlockingDate(today.minusMonths(2).toDate());
        Assert.assertFalse(filter.doFilter(currentBrin));

        currentBrin.setEradicationDate(today.withDayOfMonth(1).withMillisOfDay(0).toDate());
        Assert.assertTrue(filter.doFilter(currentBrin));
    }


    @Test
    public void test_filterCurrentStatusAlwaysFilteredOthersNo() throws Exception {
        currentBrin.setCreationDate(today.minusMonths(2).toDate());
        Assert.assertFalse(filter.doFilter(currentBrin));

        currentBrin.setStatus(Status.toEradicate);
        Assert.assertFalse(filter.doFilter(currentBrin));

        currentBrin.setStatus(Status.eradicated);
        Assert.assertFalse(filter.doFilter(currentBrin));

        currentBrin.setStatus(Status.current);
        Assert.assertTrue(filter.doFilter(currentBrin));
    }
}
