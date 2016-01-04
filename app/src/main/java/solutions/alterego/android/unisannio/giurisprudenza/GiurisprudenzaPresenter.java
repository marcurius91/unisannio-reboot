package solutions.alterego.android.unisannio.giurisprudenza;

import org.jsoup.nodes.Document;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;
import solutions.alterego.android.unisannio.models.Article;

class GiurisprudenzaPresenter implements IGiurisprudenzaPresenter {

    private IParser<Article> mParser;

    private IRetriever<Document> mRetriver;

    private final GiurisprudenzaView view;

    GiurisprudenzaPresenter(GiurisprudenzaView view, String url) {
        this.view = view;

        mParser = new GiurisprudenzaParser();
        mRetriver = new GiurisprudenzaRetriever(url);
    }

    @Override
    public void getArticles() {
        mRetriver.retriveDocument()
            .map(document -> mParser.parse(document))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<Article>>() {
                @Override
                public void onCompleted() {
                    view.stopProgress();
                }

                @Override
                public void onError(Throwable e) {
                    view.stopProgress();
                }

                @Override
                public void onNext(List<Article> list) {
                    view.setArticles(list);
                }
            });
    }
}
