package solutions.alterego.android.unisannio.scienze

import org.jsoup.nodes.Document
import org.junit.After
import org.junit.Before
import org.junit.Test
import solutions.alterego.android.assertThat

class ScienzeDetailRetriverTest {

    val url = "http://www.sciunisannio.it/avvisi/56-various/4842-esito-prove-esame-profssa-stilo";
    lateinit var document : Document;
    val retriver = ScienzeDetailRetriver(url);

    @Before
    fun setUp(){
    assertThat(url).isNotNull;
    }

    @After
    fun tearDown(){
    }

    @Test
    fun testRetriveDetail() {
        document = retriver.document;
        assertThat(document).isNotNull;
    }
}