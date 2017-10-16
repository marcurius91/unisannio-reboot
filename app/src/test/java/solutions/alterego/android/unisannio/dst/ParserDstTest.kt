package solutions.alterego.android.unisannio.dst

import assertk.assertions.isGreaterThan
import org.jsoup.nodes.Document
import org.junit.Test
import rx.observers.TestSubscriber
import solutions.alterego.android.MockitoRxTest
import solutions.alterego.android.unisannio.models.Article

@Suppress("IllegalIdentifier")
class ParserDstTest : MockitoRxTest() {

    @Test
    fun `parse DST article from DST document`() {
        val retriever = RetrieverDst()
        val retrieverSubscriber = TestSubscriber<Document>()
        retriever.retrieve(FacultyDst.sections[0].url).subscribe(retrieverSubscriber)
        val document = retrieverSubscriber.onNextEvents.first()

        val parser = ParserDst()

        val parserSubscriber = TestSubscriber<List<Article>>()

        parser.parse(document).subscribe(parserSubscriber)

        parserSubscriber.assertCompleted()
        parserSubscriber.assertNoErrors()

        val articles = parserSubscriber.onNextEvents.first()

        assertk.assert(articles.size).isGreaterThan(0)
    }

}
