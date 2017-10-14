package solutions.alterego.android.unisannio.repository

import org.jsoup.nodes.Document
import rx.Single
import solutions.alterego.android.unisannio.models.Article

interface Parser {
    fun parse(document: Document): Single<List<Article>>
}

class ArticleParser : Parser {
    override fun parse(document: Document): Single<List<Article>> {
        return Single.fromCallable {
            document.select("item")
                .asSequence()
                .map {
                    Article(
                        id = it.select("guid").first()?.text().orEmpty(),
                        title = it.select("title").first().text(),
                        author = it.select("author").first().text(),
                        url = it.select("link").first().text(),
                        body = it.select("description").first().text(),
                        date = it.select("pubDate").first()?.text().orEmpty()
                    )
                }
                .toList()
        }
    }
}
