package solutions.alterego.android.unisannio.giurisprudenza;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.URLS;

public class GiurisprudenzaComunicazioniRetriever implements IGiurisprudenzaRetriever{
    @Override
    public Observable<Document> get() {
        return Observable
                .create(new Observable.OnSubscribe<Document>() {

                    @Override
                    public void call(Subscriber<? super Document> subscriber) {

                        Document doc = null;

                        try {
                            doc = getDocument();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        if (doc != null) {
                            subscriber.onNext(doc);
                            //Log.e("RETRIVER",doc.getAllElements().toString());
                            subscriber.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    public Document getDocument() throws IOException {
        return Jsoup.connect(URLS.GIURISPRUDENZA_COMUNICAZIONI_RSS).timeout(10 * 1000).get();
    }
}
