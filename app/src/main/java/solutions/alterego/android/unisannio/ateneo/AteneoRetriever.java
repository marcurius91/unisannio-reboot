package solutions.alterego.android.unisannio.ateneo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public class AteneoRetriever implements IRetriever<Document>{

    /*public Observable<List<Article>> getNewsList(final boolean studenti) {
        return Observable
                .create(new Observable.OnSubscribe<List<Article>>() {
                    @Override
                    public void call(Subscriber<? super List<Article>> subscriber) {
                        List<Article> list = get(studenti);
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<Article> get(boolean studenti) {
        String url;
        IParser parser;
        if (studenti) {
            url = URLS.ATENEO_STUDENTI_NEWS;
            // Al momento il parser e` lo stesso perche` le due pagine sono simili.
            // Teniamo la porta aperta in caso ci fossero cambiamenti al sito.
            parser = new AteneoAvvisiParser();
        } else {
            url = URLS.ATENEO_NEWS;
            parser = new AteneoAvvisiParser();
        }

        List<Article> newsList;
        try {
            Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
            newsList = parser.parse(doc);
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return newsList;
    }*/

    private String urlToRetrieve;
    private List<String> urlsToRetrieve = new ArrayList<>();

    public AteneoRetriever(String url_retrieve){
        //this.urlToRetrieve = url_retrieve;

        //Fill the arrayList with all the urls based on the ATENEO_NEWS URL from page 0 to page 41.
        for(int i = 0; i < 42; i++){
            urlsToRetrieve.add(url_retrieve.concat("?page=").concat(String.valueOf(i)));
        }
    }


    @Override
    public Observable<Document> retriveDocument() {

        final List<Document> documents = new ArrayList<>();

        Subscription urlsubscription = loadUrlList().subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }


            public void onNext(String s) {
                try {
                    Document doc = getDocument(s);
                    documents.add(doc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /*return Observable
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
                }).subscribeOn(Schedulers.io());*/

        return Observable.from(documents).subscribeOn(Schedulers.io());

    }
    private Observable<String> loadUrlList(){

        Observable<String> urltoRetrieve = Observable.from(urlsToRetrieve);

            return urltoRetrieve;
    }

    public Document getDocument(String urlToRetrieve) throws IOException {
        return Jsoup.connect(urlToRetrieve).timeout(10 * 1000).userAgent("Mozilla").get();
    }
}
