package solutions.alterego.android.unisannio.repository

import rx.Observable
import solutions.alterego.android.unisannio.Faculty
import solutions.alterego.android.unisannio.models.Article

interface Repository {
    fun loadArticles(aculty: Faculty): Observable<List<Article>>
}

class ArticleRepository (
    private val parser: Parser,
    private val retriever: Retriever
) : Repository {

    override fun loadArticles(faculty: Faculty): Observable<List<Article>> = fetchFromNetwork(faculty)

    private fun fetchFromNetwork(faculty: Faculty): Observable<List<Article>> {
        return retriever.retrieve(faculty.sections[0].url)
            .flatMap { parser.parse(it) }
            .toObservable()
    }
}
