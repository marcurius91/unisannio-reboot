package solutions.alterego.android.unisannio.ateneo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.interfaces.IRetriever;
import timber.log.Timber;

public class AteneoRetriever implements IRetriever<Document> {

    private String baseUrl;

    public AteneoRetriever(String url) {

        baseUrl = url;
    }

    @Override
    public Observable<Document> retrieveDocument() {
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
        return Jsoup.connect(baseUrl).timeout(10 * 1000).userAgent("Mozilla").get();
    }
}

/*
class DocumentRetriever @Inject constructor() : Retriever {
    override fun retrieve(url: String): Single<Document> {
        return Single.fromCallable {
            Jsoup.connect(url)
                    .timeout(10 * 1000)
                    .parser(org.jsoup.parser.Parser.xmlParser())
                    .get()
        }
    }
}
*/