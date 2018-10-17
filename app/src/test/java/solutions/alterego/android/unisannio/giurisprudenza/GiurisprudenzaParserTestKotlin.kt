package solutions.alterego.android.unisannio.giurisprudenza

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import solutions.alterego.android.assertThat
import solutions.alterego.android.unisannio.URLS
import kotlin.test.assertTrue


class GiurisprudenzaParserTestKotlin {

    val url = URLS.GIURISPRUDENZA_AVVISI;
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
    fun testArticleListConsistency() {
        val list = parser.parse(document);
       // assertThat(list.size > 0);
        assertThat(list.isNotEmpty());
        val date = list.get(0).date;
        val author = list.get(0).author;
        val title = list.get(0).title;

        assertThat(author).isNotNull;
        assertThat(title).isNotNull;
        assertTrue(title.isNotEmpty())

        val year = date.toString().substring(0, 4).toInt();
        assertThat(year).isEqualTo(2018)
    }
}
