package solutions.alterego.android.unisannio.ateneo

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import rx.Observable
import solutions.alterego.android.unisannio.interfaces.IRetriever
import timber.log.Timber

class AteneoRetriever(private val baseUrl: String) : IRetriever<Document> {

    val urls = mutableListOf<String>()

    init {
        (0..42).mapTo(urls, { "$baseUrl?page=$it" })
    }

    override fun retrieveDocument(): Observable<Document> {
        return Observable.from(urls)
            .map {
                Timber.d("Url: $it")
                Jsoup.connect(it).timeout(10 * 1000).userAgent("Mozilla").get()
            }
            .doOnNext {
                Timber.d("Document ${it.location()}")
            }
    }
}
