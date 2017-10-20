package solutions.alterego.android.unisannio.scienze

import java.util.ArrayList
import java.util.Locale
import java.util.UUID
import org.joda.time.format.DateTimeFormat
import org.jsoup.nodes.Document
import solutions.alterego.android.unisannio.URLS
import solutions.alterego.android.unisannio.interfaces.Parser
import solutions.alterego.android.unisannio.models.Article
import solutions.alterego.android.unisannio.utils.DateUtils
import solutions.alterego.android.unisannio.utils.*

class ScienzeParser : Parser<Article> {

    private val AUTHOR = "Didattica"

    override fun parse(document: Document): List<Article> {

        val elements = document.body().select("div.blog").select("div.item")
        val newsList = ArrayList<Article>()

        var date: String?
        var title: String?
        var body: String?
        var url: String?

        for (element in elements) {

            val titleElement = element.select("h2 >  a[href]").first()
            title = titleElement.text()

            val bodyElement = element.select("p").first()
            body = bodyElement.text()

            val dateElement = element.select("dd.published").first()
            date = DateUtils.convertMonthFromScienze(dateElement.text())

            val dtf = DateTimeFormat.forPattern("dd/MM/yyyy")
            val jodatime = dtf.withLocale(Locale.ITALIAN).parseDateTime(date!!.replace(".", " ").toLowerCase())

            val linkElement = element.select("h2 > a")
            url = URLS.SCIENZE + linkElement.attr("href")

            newsList.add(Article(UUID.randomUUID().toString(), title!!, AUTHOR, url, body!!, jodatime.toIso8601()))
        }
        return newsList
    }
}
