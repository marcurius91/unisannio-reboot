package solutions.alterego.android.unisannio.ateneo;

import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaRetriever;

import static org.junit.Assert.*;

/**
 * Created by 667198 on 18/10/2018.
 */
public class AteneoRetrieverTest {
    public String url;
    public AteneoRetriever retriver ;
    public Document document;

    @Before
    public void setUp() throws Exception {

        url = URLS.ATENEO_NEWS;
        //lateinit var document : Document;
        retriver = new AteneoRetriever(url);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void retrieveDocument() throws Exception {
        document = retriver.getDocument();
        assertNotNull(document);

    }

    @Test
    public void getDocument() throws Exception {
        assertNotNull(url);
    }

}