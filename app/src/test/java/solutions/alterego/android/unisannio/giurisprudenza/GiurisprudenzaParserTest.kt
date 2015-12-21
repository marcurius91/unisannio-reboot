package solutions.alterego.android.unisannio.giurisprudenza

import org.jsoup.nodes.Document
import org.junit.After
import org.junit.Before
import org.junit.Test
import solutions.alterego.android.assertThat


class GiurisprudenzaParserTest {

    val url = "http://www.giurisprudenza.unisannio.it/index.php?option=com_avvisi&controller=elenco&view=elenco&catid=2&Itemid=267";
    val retriver = GiurisprudenzaRetriever(url);
    val parser = GiurisprudenzaParser();
    lateinit var document : Document;

    @Before
    fun setUp(){
        assertThat(url).isNotNull;
        document = retriver.document;
        assertThat(document).isNotNull;
    }

    @After
    fun tearDown(){
    }

    @Test
    fun testNotNullParseDetail() {
        var list = parser.parse(document);
        assertThat(list).isNotNull;
    }
}