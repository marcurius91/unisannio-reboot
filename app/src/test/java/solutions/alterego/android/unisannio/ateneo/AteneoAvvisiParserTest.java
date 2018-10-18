package solutions.alterego.android.unisannio.ateneo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import solutions.alterego.android.unisannio.URLS;

import static org.junit.Assert.*;

/**
 * Created by 667198 on 17/10/2018.
 */
public class AteneoAvvisiParserTest {

    public String url = URLS.ATENEO_NEWS;
    public AteneoRetriever retriver = new AteneoRetriever(url);
    public AteneoAvvisiParser parser = new AteneoAvvisiParser();
   // public Document document = retriver.getUrls();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parse() throws Exception {
    }

    /*public String url = URLS.GIURISPRUDENZA_AVVISI;
    public GiurisprudenzaRetriever retriver = new GiurisprudenzaRetriever(url);
    public GiurisprudenzaParser parser = new GiurisprudenzaParser();
    public Document document = retriver.getDocument();
    public GiurisprudenzaParserTest1() throws IOException {
    }


    @Before
    public void setUp() throws Exception {
        assertNotNull(document);
        assertTrue(document.children().size()>0);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parse() throws Exception {
    }

    @Test
    public void testNotNullParseDetail() {
        List<Article> list = parser.parse(document);
        assertNotNull(list);
    }

    @Test
    public void testArticleListConsistency() {
        List<Article> list = parser.parse(document);
        assert(list.size() > 0);


        String date = list.get(1).getDate();
        String author = list.get(1).getAuthor();
        String title = list.get(1).getTitle();

        assertNotNull(author);
        assertNotNull(title);

        int year = Integer.parseInt(date.toString().substring(0, 4));
        assert(year==2018);
    }
*/

}