package solutions.alterego.android.unisannio.ateneo

import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class AteneoAvvisiPresenter {

    lateinit var view: AteneoAvvisiView

    lateinit var retriever: AteneoRetriever

    /*fun getNews(isStudenti: Boolean) {
        retriever.getNewsList(isStudenti)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }*/
}

