package net.codjo.tools.pyp.services;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import net.codjo.confluence.ConfluenceException;
import net.codjo.confluence.ConfluenceServer;
import net.codjo.confluence.ConfluenceSession;
import net.codjo.confluence.Page;
/**
 * TODO beaucoup de copier/coller de net.codjo.maven.mojo.agf.SendAnnouncementToTeamsMojo
 */
public class ConfluenceService {

    private static final int CONFLUENCE_TIMEOUT = 10000;
    private String confluenceSpaceKey;
    private String confluencePage;


    public ConfluenceService(String confluenceSpaceKey, String confluencePage) {
        this.confluenceSpaceKey = confluenceSpaceKey;
        this.confluencePage = confluencePage;
    }


    public Set<String> extractUserListFromConfluence(String confluenceUrl,
                                                     String confluenceUser,
                                                     String confluencePassword) throws ConfluenceException {
        ConfluenceServer server = new ConfluenceServer(
              new ConfluenceSession(confluenceUrl, confluenceUser, confluencePassword));
        server.setTimeout(CONFLUENCE_TIMEOUT);
        server.login();
        try {
            Page page = server.getPage(confluenceSpaceKey, confluencePage);
            return extractDeveloperList(page.getContent());
        }
        finally {
            server.logout();
        }
    }


    protected Set<String> extractDeveloperList(String confluenceContent) {
        Set<String> userList = new TreeSet<String>();
        for (StringTokenizer tokenizer = new StringTokenizer(confluenceContent, "\n");
             tokenizer.hasMoreTokens(); ) {
            String row = tokenizer.nextToken();
            if (row.startsWith("* ") && row.contains("(*g)")) {
                userList.add(extractUser(row));
            }
        }

        return userList;
    }


    private String extractUser(String row) {

        String tmp = row.substring(2).trim();
        int pos = tmp.indexOf(" ");
        if (pos != -1) {
            tmp = tmp.substring(0, pos);
        }
        if (tmp.startsWith("*")) {
            tmp = tmp.substring(1);
        }
        if (tmp.endsWith("*")) {
            tmp = tmp.substring(0, tmp.length() - 1);
        }
        return tmp;
    }
}
