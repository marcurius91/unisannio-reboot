package solutions.alterego.android.unisannio.ateneo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.models.Article;

public class AteneoRetriever {

    public Observable<List<Article>> getNewsList(final boolean studenti) {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        List<Article> list = get(studenti);
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<Article> get(boolean studenti) {
        String url;
        IParser parser;
        if (studenti) {
            url = URLS.ATENEO_STUDENTI_NEWS;
            // Al momento il parser e` lo stesso perche` le due pagine sono simili.
            // Teniamo la porta aperta in caso ci fossero cambiamenti al sito.
            parser = new AteneoAvvisiParser();
        } else {
            url = URLS.ATENEO_NEWS;
            parser = new AteneoAvvisiParser();
        }

        List<Article> newsList;
        try {
            Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
            newsList = parser.parse(doc);
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return newsList;
    }
}
