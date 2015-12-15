package solutions.alterego.android.unisannio.ingegneria

import org.junit.Before
import org.junit.Test
import solutions.alterego.android.assertThat

class IngegneriaCercapersonePresenterTest {

    val parser = IngegneriaCercapersoneParser()

    val retriever = IngegneriaCercapersoneRetriever()

    val presenter = IngegneriaCercapersonePresenter()

    @Before
    @Throws(Exception::class)
    fun setUp() {

    }

    @Test
    @Throws(Exception::class)
    fun testGetPeople() {
        val list = presenter.people.toBlocking().first()
        assertThat(list).isNotNull
    }
}