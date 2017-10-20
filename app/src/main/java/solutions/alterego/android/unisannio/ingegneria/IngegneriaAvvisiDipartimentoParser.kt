package solutions.alterego.android.unisannio.ingegneria

import org.joda.time.format.DateTimeFormat
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import solutions.alterego.android.unisannio.interfaces.Parser
import solutions.alterego.android.unisannio.models.Article
import solutions.alterego.android.unisannio.utils.DateUtils
import java.util.*

class IngegneriaAvvisiDipartimentoParser : Parser<Article> {


    override fun parse(document: Document): List<Article> {

        val list = ArrayList<Article>()

        val elements = document.select("item")


        for (element in elements) {
            val title = element.select("title").text()
            val body = clearHtml(element.select("description").text())
            val date = element.select("pubdate").text()
            val author = element.select("author").text()
            val link = element.select("guid").text()

            val dtf = DateTimeFormat.forPattern("dd/MM/yyyy")
            val jodatime = dtf.parseDateTime(DateUtils.convertMonth(date.substring(5)))

            list.add(Article(UUID.randomUUID().toString(), title, author, link, body, jodatime.toString()))
        }

        return list
    }

    private fun  clearHtml(html: String?): String {

        val tmp1 = Jsoup.clean(html, Whitelist.none())
        val removed_tag = tmp1.replace("&nbsp;", "").replace("&gt;", "")

        return removed_tag
    }
}