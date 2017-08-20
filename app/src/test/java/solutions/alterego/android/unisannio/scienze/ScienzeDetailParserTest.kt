package solutions.alterego.android.unisannio.scienze

import org.jsoup.nodes.Document
import org.junit.After
import org.junit.Before
import org.junit.Test
import solutions.alterego.android.assertThat

class ScienzeDetailParserTest {

    val url = "http://www.dstunisannio.it/index.php/scienze-biologiche-27/topic-27/767-registrazione-libretto-esame-ofa-di-chimica-scienze-biologiche-mercoledi-18-gennaio-2016";
    val retriver = ScienzeDetailRetriver(url);
    val parser = ScienzeDetailParser();
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
    fun testParseDetail() {
        var list = parser.bodyelements;
        assertThat(list).isNotNull;
    }
}