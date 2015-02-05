package solutions.alterego.android.unisannio.ingegneria;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.URLS;

public class IngegneriaAvvisiStudentiRetriever implements IngegneriaRetriever {

    public Observable<List<IngegneriaDidatticaItem>> get() {
        return Observable
                .create(new Observable.OnSubscribe<List<IngegneriaDidatticaItem>>() {
                    @Override
                    public void call(Subscriber<? super List<IngegneriaDidatticaItem>> subscriber) {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(URLS.INGEGNERIA_NEWS_STUDENTI).timeout(10 * 1000).get();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        if (doc != null) {
                            Elements elements = doc.select("#maincontent-block > #item");

                            try {
                                final List<IngegneriaDidatticaItem> items = new IngegneriaAvvisiStudentiParser().parse(elements);
                                subscriber.onNext(items);
                                subscriber.onCompleted();
                            } catch (ParseException e) {
                                subscriber.onError(e);
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}