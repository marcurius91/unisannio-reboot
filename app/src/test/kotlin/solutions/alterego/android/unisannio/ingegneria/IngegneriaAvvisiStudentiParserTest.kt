package solutions.alterego.android.unisannio.ingegneria

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import solutions.alterego.android.assertThat

@RunWith(MockitoJUnitRunner::class)
class IngegneriaAvvisiStudentiParserTest {

    private inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)

    val parser = IngegneriaAvvisiStudentiParser()

    val retriever = IngegneriaAvvisiStudentiRetriever()

    lateinit var document: Document

    val elementOne: Element = mock()

    val elementTwo: Element = mock()

    @Before
    fun setUp() {
        document = retriever.document
        assertThat(document).isNotNull
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testParse() {
        val list = parser.parse(document)
        assertThat(list).isNotNull
    }
}