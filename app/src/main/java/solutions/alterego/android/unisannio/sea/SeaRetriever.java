package solutions.alterego.android.unisannio.sea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.giurisprudenza.Article;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaParser;

public class SeaRetriever {

    public Observable<List<Article>> get(String url) {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        List<Article> newsList;
                        try {
                            Document doc = Jsoup.connect(url).timeout(10 * 1000).userAgent("Mozilla").get();

                            newsList = new SeaParser().parse(doc);
                            subscriber.onNext(newsList);
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}
