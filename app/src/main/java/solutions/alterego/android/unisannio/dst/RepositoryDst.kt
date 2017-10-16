package solutions.alterego.android.unisannio.dst

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import rx.Observable
import rx.Single
import solutions.alterego.android.unisannio.Faculty
import solutions.alterego.android.unisannio.models.Article
import javax.inject.Inject

interface Parser {
    fun parse(document: Document): Single<List<Article>>
}

interface Retriever {
    fun retrieve(url: String): Single<Document>
}

interface Repository {
    fun loadArticles(): Observable<List<Article>>
}

class RepositoryDst @Inject constructor(
    private val parser: Parser,
    private val retriever: Retriever,
    private val faculty: Faculty
) : Repository {

    override fun loadArticles(): Observable<List<Article>> = fetchFromNetwork()

    private fun fetchFromNetwork(): Observable<List<Article>> {
        return retriever.retrieve(faculty.sections[0].url)
            .flatMap { parser.parse(it) }
            .toObservable()
    }
}

class ParserDst : Parser {
    override fun parse(document: Document): Single<List<Article>> {
        return Single.fromCallable {
            document.select("item")
                .asSequence()
                .map {
                    Article(
                        id = it.select("guid").first().text(),
                        title = it.select("title").first().text(),
                        author = it.select("author").first().text(),
                        url = it.select("link").first().text(),
                        body = it.select("description").first().text(),
                        date = it.select("pubDate").first().text()
                    )
                }
                .toList()
        }
    }
}

class RetrieverDst : Retriever {
    override fun retrieve(url: String): Single<Document> {
        return Single.fromCallable {
            Jsoup.connect(url)
                .timeout(10 * 1000)
                .parser(org.jsoup.parser.Parser.xmlParser())
                .get()
        }
    }
}
