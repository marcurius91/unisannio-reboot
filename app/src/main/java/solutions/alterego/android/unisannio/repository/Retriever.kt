package solutions.alterego.android.unisannio.repository

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import rx.Single
import javax.inject.Inject

interface Retriever {
    fun retrieve(url: String): Single<Document>
}

class DocumentRetriever @Inject constructor() : Retriever {
    override fun retrieve(url: String): Single<Document> {
        return Single.fromCallable {
            Jsoup.connect(url)
                .timeout(10 * 1000)
                .parser(org.jsoup.parser.Parser.xmlParser())
                .get()
        }
    }
}
