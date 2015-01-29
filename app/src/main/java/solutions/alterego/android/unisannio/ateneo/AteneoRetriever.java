package solutions.alterego.android.unisannio.ateneo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AteneoRetriever {

    public Observable<List<AteneoNews>> getNewsList(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<List<AteneoNews>>() {
                    @Override
                    public void call(Subscriber<? super List<AteneoNews>> subscriber) {
                        List<AteneoNews> list = get(url);
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<AteneoNews> get(String url) {
        List<AteneoNews> newsList;
        try {
            Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
            newsList = new AteneoParser().parse(doc);
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return newsList;
    }
}
