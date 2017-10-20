package solutions.alterego.android.unisannio.ateneo

import java.util.ArrayList
import org.joda.time.format.DateTimeFormat
import org.jsoup.nodes.Document
import solutions.alterego.android.unisannio.interfaces.Parser
import solutions.alterego.android.unisannio.models.Article
import solutions.alterego.android.unisannio.utils.*

class AteneoAvvisiParser : Parser<Article> {

    override fun parse(document: Document): List<Article> {

        val newsList = ArrayList<Article>()
        var date: String? = null
        var title: String? = null
        var id: String? = null

        val newsItems = document.select("div.view-content").select("tbody").select("tr")

        for (i in 1..newsItems.size - 1) {
            val bodyElement = newsItems[i].select("td")
            if (bodyElement != null) {

                //get the id of the news.
                val id_element = bodyElement[0]
                id = id_element.text()

                //get the Title of the news.
                val title_element = bodyElement[2].select("a").first()
                title = title_element.text()

                //get the date of the news
                val date_element = bodyElement[3].select("span").first()
                date = date_element.text()
            }

            if (title != null && date != null) {
                val dtf = DateTimeFormat.forPattern("dd/MM/yyyy")
                val jodatime = dtf.parseDateTime(date)
                newsList.add(Article(id!!, title, "", "", "", jodatime.toIso8601()))
            }
        }

        return newsList
    }
}

