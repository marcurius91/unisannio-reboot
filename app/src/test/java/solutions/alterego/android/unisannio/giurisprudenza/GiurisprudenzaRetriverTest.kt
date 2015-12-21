package solutions.alterego.android.unisannio.giurisprudenza

import org.jsoup.nodes.Document
import org.junit.After
import org.junit.Before
import org.junit.Test
import solutions.alterego.android.assertThat

class GiurisprudenzaRetriverTest {

    val url = "http://www.giurisprudenza.unisannio.it/index.php?option=com_avvisi&controller=elenco&view=elenco&catid=2&Itemid=267";
    lateinit var document : Document;
    val retriver = GiurisprudenzaRetriever(url);

    @Before
    fun setUp(){
        assertThat(url).isNotNull;
    }

    @After
    fun tearDown(){
    }

    @Test
    fun testRetriveArticles() {
        document = retriver.document;
        assertThat(document).isNotNull;
    }
}