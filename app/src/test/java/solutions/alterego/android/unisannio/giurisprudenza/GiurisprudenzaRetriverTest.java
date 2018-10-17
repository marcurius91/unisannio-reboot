package solutions.alterego.android.unisannio.giurisprudenza;

import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import solutions.alterego.android.unisannio.URLS;

import static org.junit.Assert.assertNotNull;

/**
 * Created by 667198 on 16/10/2018.
 */

public class GiurisprudenzaRetriverTest {
    public String url = URLS.GIURISPRUDENZA_AVVISI;
    //lateinit var document : Document;
    public GiurisprudenzaRetriever retriver = new GiurisprudenzaRetriever(url);
    public Document document;

    @Before
    public void setUp(){
        assertNotNull(url);
    }

    @After
    public void tearDown(){
    }

    @Test
    public void testRetriveArticles() throws IOException {
        document = retriver.getDocument();
        assertNotNull(document);
    }
}
