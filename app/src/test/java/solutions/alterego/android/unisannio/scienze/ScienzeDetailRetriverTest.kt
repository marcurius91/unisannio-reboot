package solutions.alterego.android.unisannio.scienze

import org.jsoup.nodes.Document
import org.junit.After
import org.junit.Before
import org.junit.Test
import solutions.alterego.android.assertThat

class ScienzeDetailRetriverTest {

    val url = "http://www.dstunisannio.it/index.php/scienze-biologiche-27/topic-27/767-registrazione-libretto-esame-ofa-di-chimica-scienze-biologiche-mercoledi-18-gennaio-2016";
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