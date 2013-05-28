package net.codjo.tools.pyp;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import java.io.File;
import net.codjo.test.common.fixture.DirectoryFixture;
import net.codjo.util.file.FileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static net.codjo.test.common.matcher.JUnitMatchers.*;
public class PypApplicationTest {
    private DirectoryFixture fixture = new DirectoryFixture("target/pyp");


    @Before
    public void setup() throws Exception {
        fixture.doSetUp();
        FileUtil.copyFile(new File(getClass().getResource("/PypRepository.xml").toURI()),
                          new File(fixture.getCanonicalFile(), "PypRepository.xml"));
        PypServer.main(new String[]{});
    }


    @After
    public void tearDown() throws Exception {
        PypServer.stopServer();
        fixture.doTearDown();
    }


    @Test
    public void test_mountSharedResource() throws Exception {
        WebClient webClient = new WebClient();
        final String url = PypServer.getServerUrl() + PypApplication.XML_REPOSITORY_PATH;
        final Page page = webClient.getPage(url);

        String repositoryContent = FileUtil.loadContent(getClass().getResource("/PypRepository.xml"));
        assertThat(page.getWebResponse().getContentAsString(), is(repositoryContent));
    }
}
