package solutions.alterego.android.unisannio.scienze

import org.jsoup.nodes.Document
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import solutions.alterego.android.assertThat

@RunWith(MockitoJUnitRunner::class)
class ScienzeParserTest {

    val url = "http://www.sciunisannio.it";
    val parser = ScienzeParser();
    val retriver = ScienzeRetriever(url);
    lateinit var document: Document


    @Before
    fun setUp(){
    document = retriver.document;
    assertThat(document).isNotNull;
    }

    @After
    fun tearDown(){
    }

    @Test
    fun testParser() {
        val list = parser.parse(document);
        assertThat(list).isNotNull;

    }
}