package solutions.alterego.android.unisannio.ingegneria

import org.joda.time.format.DateTimeFormat
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import solutions.alterego.android.unisannio.interfaces.Parser
import solutions.alterego.android.unisannio.models.Article
import solutions.alterego.android.unisannio.utils.toIso8601
import java.util.*

class IngegneriaAvvisiStudentiParser : Parser<Article> {

    val EMPTY_AUTHOR_PLACEHOLDER = "Presidio didattico"
    var SELECT_ELEMENTS = "#maincontent-block > #item"

    override fun parse(document: Document): List<Article> {
        val elements = document.select("div.item")

        val list = ArrayList<Article>()

        for (i in elements.indices) {

            //get the title of the news
            val title_element = elements[i].select("h2").select("a").select("span.avvtitle")
            val title = title_element.text()

            //get the url of the news
            val url_element = elements[i].select("h2").select("a").first()
            val url = url_element.attr("href")

            //get the body of the news
            val body_element = elements[i].select("div.avvtext").first()
            val body = body_element.text()

            //Extract date from publish field of the news
            val publish_element = elements[i].select("div").select("p.publishinfo").first()
            val publish = publish_element.text()
            val st = StringTokenizer(publish, " ")
            val st1 = st.nextToken()
            val st2 = st.nextToken()
            val date = st.nextToken()

            val dtf = DateTimeFormat.forPattern("dd/MM/yyyy")
            val jodatime = dtf.parseDateTime(date)

            //get the author of the news
            val authors_elements = elements[i].select("div").select("p").last()
            var author: String?

            if (authors_elements.text().contains("Autore")) {
                author = authors_elements.text().substring(7)
            } else
                author = EMPTY_AUTHOR_PLACEHOLDER

            list.add(Article(UUID.randomUUID().toString(), title, author, url, body, jodatime.toIso8601()))
        }

        return list
    }

    fun getElements(document: Document): Elements {
        return document.select(SELECT_ELEMENTS)
    }
}