package solutions.alterego.android.unisannio.giurisprudenza

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import solutions.alterego.android.assertThat
import kotlin.test.assertTrue

@Ignore
class GiurisprudenzaParserTest {

    val url = "http://www.giurisprudenza.unisannio.it/index.php?option=com_avvisi&controller=elenco&view=elenco&catid=2&Itemid=267";
    val retriver = GiurisprudenzaRetriever(url);
    val parser = GiurisprudenzaParser();
    val document = retriver.document

    @Before
    fun setUp() {
        assertThat(document).isNotNull;
        assertThat(document.children().size > 0)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testNotNullParseDetail() {
        val list = parser.parse(document);
        assertThat(list).isNotNull;
    }

    @Test
    @Ignore
    fun testArticleListConsistency() {
        val list = parser.parse(document);
        assertThat(list.size > 0)

        val date = list.get(1).date;
        val author = list.get(1).author;
        val title = list.get(1).title;

        assertThat(author).isNotNull;
        assertThat(title).isNotNull;
        assertTrue(title.isNotEmpty())

        val year = date.toString().substring(0, 4).toInt();
        assertThat(year).isEqualTo(2017)
    }
}
