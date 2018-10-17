package solutions.alterego.android.unisannio.giurisprudenza;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.jsoup.nodes.Document;

import java.util.List;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.models.Article;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.*;

/**
 * Created by 667198 on 17/10/2018.
 */
public class GiurisprudenzaParserTest1 {

    public String url = URLS.GIURISPRUDENZA_AVVISI;
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

}