package solutions.alterego.android.unisannio.giurisprudenza

import org.jsoup.nodes.Document
import org.junit.After
import org.junit.Before
import org.junit.Test
import solutions.alterego.android.assertThat
import solutions.alterego.android.unisannio.URLS

class GiurisprudenzaRetriverTestKotlin {

    val url = URLS.GIURISPRUDENZA_AVVISI;
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