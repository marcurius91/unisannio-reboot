@file:Suppress("IllegalIdentifier")

package solutions.alterego.android.unisannio.dst

import assertk.assert
import assertk.assertions.isEqualTo
import org.jsoup.nodes.Document
import org.junit.Test
import rx.observers.TestSubscriber
import solutions.alterego.android.MockitoRxTest

class RetrieverDstTest : MockitoRxTest() {

    @Test
    fun `retrieve DST RSS`() {
        val retriever = RetrieverDst()

        val subscriber = TestSubscriber<Document>()
        retriever.retrieve(FacultyDst.sections[0].url).subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertCompleted()

        val document = subscriber.onNextEvents.first()
        val title = document.select("title")[0].text()

        assert(title).isEqualTo("DST | Didattica :: notizie")
    }
}
