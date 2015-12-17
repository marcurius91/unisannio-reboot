package solutions.alterego.android.unisannio.scienze;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IRetriever;



public class ScienzeDetailRetriver implements IRetriever<Document>{

    String url;

    public ScienzeDetailRetriver(String urlToRetrive){
        this.url = urlToRetrive;
    }

    @Override
    public Observable<Document> retriveDocument() {
        return Observable
                .create(new Observable.OnSubscribe<Document>() {
                    @Override
                    public void call(Subscriber<? super Document> subscriber) {

                        Document doc = null;
                        try {
                            doc = Jsoup.connect(url).ignoreContentType(true).timeout(10 * 1000).get();
                        }
                        catch (IOException e) {
                            subscriber.onError(e);
                        }

                        if(doc != null){
                            subscriber.onNext(doc);
                            subscriber.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }
}
