package solutions.alterego.android.unisannio.dst

import assertk.assertions.isGreaterThan
import org.jsoup.nodes.Document
import org.junit.Test
import rx.observers.TestSubscriber
import solutions.alterego.android.MockitoRxTest
import solutions.alterego.android.unisannio.Dst
import solutions.alterego.android.unisannio.Giurisprudenza
import solutions.alterego.android.unisannio.models.Article
import solutions.alterego.android.unisannio.repository.ArticleParser
import solutions.alterego.android.unisannio.repository.DocumentRetriever

@Suppress("IllegalIdentifier")
class ArticleParserTest : MockitoRxTest() {

    @Test
    fun `parse DST article from DST document`() {
        val retriever = DocumentRetriever()
        val retrieverSubscriber = TestSubscriber<Document>()
        retriever.retrieve(Dst.sections[0].url).subscribe(retrieverSubscriber)
        val document = retrieverSubscriber.onNextEvents.first()

        val parser = ArticleParser()

        val parserSubscriber = TestSubscriber<List<Article>>()

        parser.parse(document).subscribe(parserSubscriber)

        parserSubscriber.assertCompleted()
        parserSubscriber.assertNoErrors()

        val articles = parserSubscriber.onNextEvents.first()

        assertk.assert(articles.size).isGreaterThan(0)
    }

    @Test
    fun `parse Giurisprudenza articles from Giurisprudenza document`() {
        val retriever = DocumentRetriever()
        val retrieverSubscriber = TestSubscriber<Document>()
        retriever.retrieve(Giurisprudenza.sections[0].url).subscribe(retrieverSubscriber)
        val document = retrieverSubscriber.onNextEvents.first()

        val parser = ArticleParser()

        val parserSubscriber = TestSubscriber<List<Article>>()

        parser.parse(document).subscribe(parserSubscriber)

        parserSubscriber.assertCompleted()
        parserSubscriber.assertNoErrors()

        val articles = parserSubscriber.onNextEvents.first()

        assertk.assert(articles.size).isGreaterThan(0)
    }
}
