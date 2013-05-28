package net.codjo.tools.pyp.services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import net.codjo.tools.pyp.model.Brin;
import net.codjo.tools.pyp.model.Status;
import net.codjo.tools.pyp.model.Team;
import net.codjo.tools.pyp.model.UnblockingType;
import org.junit.Assert;
import org.junit.Test;
/**
 *
 */
public class CsvServiceTest extends TestCase {

    @Test
    public void test_exportempty() throws Exception {
        StringBuilder export = CsvService.export(new ArrayList<Brin>());
        Assert.assertEquals(115, export.length());
        Assert.assertEquals(
              "title;creationDate;status;description;affectedTeams;unblockingType;unblockingDate;unblockingDescription;rootCause;\n",
              export.toString());
    }


    @Test
    public void test_exportList() throws Exception {
        List<Brin> brinList = new ArrayList<Brin>();
        Brin brin = new Brin("a title");
        brin.setCreationDate(CsvService.DATE_FORMAT.parse("28/11/2011"));
        brin.setStatus(Status.unblocked);
        brin.setDescription("a description très compliquée");
        brin.setAffectedTeams(Arrays.asList(Team.focs, Team.wam));
        brin.setunblockingType(UnblockingType.medium);
        brin.setUnBlockingDate(CsvService.DATE_FORMAT.parse("30/11/2011"));
        brin.setUnBlockingDescription("toto\ntata a la ligne");
        brin.setRootCause("root cause \ndu problème");
        brin.setEradicationDate(CsvService.DATE_FORMAT.parse("31/11/2011"));
        brinList.add(brin);

        Brin emptyBrin = new Brin("empty");
        brinList.add(emptyBrin);

        StringBuilder export = CsvService.export(brinList);

        String[] split = export.toString().split(";\n");
        Assert.assertEquals(
              "title;creationDate;status;description;affectedTeams;unblockingType;unblockingDate;unblockingDescription;rootCause",
              split[0]);
        Assert.assertEquals(
              "a title;28/11/2011;unblocked;\"a description très compliquée\";[focs, wam];medium;30/11/2011;\"toto\ntata a la ligne\";\"root cause \ndu problème\";01/12/2011",
              split[1]);
        Assert.assertEquals(
              "empty;" + CsvService.DATE_FORMAT.format(new Date()) + ";current;\"\";;;;\"\";\"\";",
              split[2]);
    }
}
