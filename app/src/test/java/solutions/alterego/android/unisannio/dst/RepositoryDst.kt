@file:Suppress("IllegalIdentifier")

package solutions.alterego.android.unisannio.dst

import assertk.assert
import assertk.assertions.isEqualTo
import org.jsoup.nodes.Document
import org.junit.Test
import rx.observers.TestSubscriber
import solutions.alterego.android.MockitoRxTest
import solutions.alterego.android.unisannio.Dst
import solutions.alterego.android.unisannio.repository.DocumentRetriever

class DocumentRetrieverTest : MockitoRxTest() {

    @Test
    fun `retrieve DST RSS`() {
        val retriever = DocumentRetriever()

        val subscriber = TestSubscriber<Document>()
        retriever.retrieve(Dst.sections[0].url).subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertCompleted()

        val document = subscriber.onNextEvents.first()
        val title = document.select("title")[0].text()

        assert(title).isEqualTo("DST | Didattica :: notizie")
    }
}
