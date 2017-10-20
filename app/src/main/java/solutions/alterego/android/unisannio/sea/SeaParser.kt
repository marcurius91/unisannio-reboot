package solutions.alterego.android.unisannio.sea

import org.joda.time.DateTime
import org.jsoup.nodes.Document
import solutions.alterego.android.unisannio.URLS
import solutions.alterego.android.unisannio.interfaces.Parser
import solutions.alterego.android.unisannio.models.Article
import solutions.alterego.android.unisannio.utils.toIso8601
import timber.log.Timber
import java.util.*

class SeaParser : Parser<Article> {
    override fun parse(document: Document): List<Article> {

        val articles = ArrayList<Article>()
        val elements = document.select("div.moduletable")
        val titles = elements.select("h4.jazin-title")

        for (i in titles.indices) {

            val link = titles.select("a").first()
            val url = URLS.SEA + link.attr("href")
            val title = titles[i].text()

            Timber.d("LINK ARTICLE", url)
            articles.add(Article(UUID.randomUUID().toString(), title, "", link.attr("href"), "", DateTime.now().toIso8601()))
        }

        return articles
    }
}