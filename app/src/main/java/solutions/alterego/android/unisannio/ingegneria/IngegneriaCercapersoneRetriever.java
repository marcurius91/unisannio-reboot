package solutions.alterego.android.unisannio.ingegneria;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public class IngegneriaCercapersoneRetriever implements IRetriever<Document> {

    @Inject
    public IngegneriaCercapersoneRetriever() {
    }

    @Override
    public Observable<Document> retrieveDocument() {
        return Observable
                .create(new Observable.OnSubscribe<Document>() {
                    @Override
                    public void call(Subscriber<? super Document> subscriber) {

                        Document doc = null;
                        try {
                            doc = Jsoup.connect(URLS.RSS_FEED_INGEGNERIA).ignoreContentType(true).timeout(10 * 1000).get();
                        }
                        catch (IOException e) {
                            subscriber.onError(e);
                        }

                        if(doc != null){
                        subscriber.onNext(doc);
                            //Log.e("RETRIVER",doc.getAllElements().toString());
                        subscriber.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.io());

    }

    }


