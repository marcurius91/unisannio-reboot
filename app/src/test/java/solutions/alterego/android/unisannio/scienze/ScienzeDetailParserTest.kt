package solutions.alterego.android.unisannio.scienze

import org.jsoup.nodes.Document
import org.junit.After
import org.junit.Before
import org.junit.Test
import solutions.alterego.android.assertThat

class ScienzeDetailParserTest {

    val url = "http://www.sciunisannio.it/avvisi/56-various/4773-spostamento-orario-e-aula-tutorati-matematica-22-dicembre-2015";
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