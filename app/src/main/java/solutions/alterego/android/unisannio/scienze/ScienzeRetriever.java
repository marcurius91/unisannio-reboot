package solutions.alterego.android.unisannio.scienze;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.models.Article;

public class ScienzeRetriever {
    public static Observable<List<Article>> get(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(url).timeout(10 * 1000).get();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        List<Article> newsList = new ScienzeParser().parse(doc);
                        subscriber.onNext(newsList);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
