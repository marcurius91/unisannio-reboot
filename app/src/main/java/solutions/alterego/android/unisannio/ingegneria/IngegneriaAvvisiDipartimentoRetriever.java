package solutions.alterego.android.unisannio.ingegneria;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.URLS;

public class IngegneriaAvvisiDipartimentoRetriever implements IngegneriaRetriever {

    @Override
    public Observable<List<Article>> get() {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(URLS.INGEGNERIA_NEWS_DIPARTIMENTO).timeout(10 * 1000).get();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        if (doc != null) {
                            final List<Article> items = new IngegneriaAvvisiDipartimentoParser().parse(doc);
                            subscriber.onNext(items);
                            subscriber.onCompleted();
                        }
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}
