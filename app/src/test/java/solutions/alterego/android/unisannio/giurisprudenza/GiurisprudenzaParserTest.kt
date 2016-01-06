package solutions.alterego.android.unisannio.giurisprudenza

import org.jsoup.nodes.Document
import org.junit.After
import org.junit.Before
import org.junit.Test
import solutions.alterego.android.assertThat
import solutions.alterego.android.unisannio.models.Article
import java.text.SimpleDateFormat
import kotlin.test.assertEquals

class GiurisprudenzaParserTest {

    val url = "http://www.giurisprudenza.unisannio.it/index.php?option=com_avvisi&controller=elenco&view=elenco&catid=2&Itemid=267";
    val retriver = GiurisprudenzaRetriever(url);
    val parser = GiurisprudenzaParser();
    lateinit var document: Document;

    @Before
    fun setUp() {
        assertThat(url).isNotNull;
        document = retriver.document;
        assertThat(document).isNotNull;
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testNotNullParseDetail() {
        var list = parser.parse(document);
        assertThat(list).isNotNull;
    }

    @Test
    fun testArticleListConsistency() {
        var list = parser.parse(document);
        var date = list.get(1).date;
        var author = list.get(1).author;
        var title = list.get(1).title;

        //check if author and title are not null
        assertThat(author).isNotNull;
        assertThat(title).isNotNull;

        //get year from date
        var year = date.toString().substring(0,4).toInt();

        //check if year is 2016
        assertEquals(year,2016);
    }
}
