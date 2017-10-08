package solutions.alterego.android.unisannio.ateneo

import org.jsoup.nodes.Document
import rx.Observable
import solutions.alterego.android.unisannio.interfaces.IParser
import solutions.alterego.android.unisannio.interfaces.IRetriever
import solutions.alterego.android.unisannio.models.Article

class AteneoPresenter(url: String) : IAvvisiPresenter {

    private val mParser: IParser<Article>

    private val retriever: IRetriever<Document>

    init {
        mParser = AteneoAvvisiParser()
        retriever = AteneoRetriever(url)
    }

    override fun getArticles(): Observable<List<Article>> {
        return retriever.retrieveDocument()
            .map { document -> mParser.parse(document) }
    }
}
