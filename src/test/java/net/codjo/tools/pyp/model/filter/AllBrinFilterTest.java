package net.codjo.tools.pyp.model.filter;
import net.codjo.tools.pyp.model.Brin;
import org.joda.time.DateTime;
import org.junit.Test;

import static net.codjo.test.common.matcher.JUnitMatchers.*;
import static org.junit.Assert.assertNull;
public class AllBrinFilterTest {
    BrinFilter filter = new AllBrinFilter("brinId", "toto", new DateTime());


    @Test
    public void test_nullBrinIsOk() throws Exception {
        assertThat(filter.doFilter(null), is(true));
    }


    @Test
    public void test_veryOldBrinIsOk() throws Exception {
        Brin veryOldBrin = new Brin("veryOldBrin");
        veryOldBrin.setCreationDate(new DateTime().withYear(1900).toDate());

        assertThat(filter.doFilter(veryOldBrin), is(true));
    }


    @Test
    public void test_firstAcceptableDateIsNull() throws Exception {
        assertNull(((AllBrinFilter)filter).getFirstAcceptableDate());
    }
}
