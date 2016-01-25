package solutions.alterego.android.unisannio.sea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.models.Article;

public class SeaRetriever {

    String urlRetrive;

    public SeaRetriever(String url){
        this.urlRetrive = url;
    }

    public Observable<List<Article>> get() {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        List<Article> newsList;
                        try {
                            Document doc = Jsoup.connect(urlRetrive).timeout(10 * 1000).userAgent("Mozilla").get();

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
