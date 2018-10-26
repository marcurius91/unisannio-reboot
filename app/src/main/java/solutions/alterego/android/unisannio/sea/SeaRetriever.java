package solutions.alterego.android.unisannio.sea;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.models.Article;

public class SeaRetriever {

        private List<String> urlsToRetrieve = new ArrayList<>();
        int index = 0;
        ArrayList<Article> global_article_list = new ArrayList<>();


        public SeaRetriever(String url){
                for(int i = 0; i < 3; i++){
                        urlsToRetrieve.add(url.concat("?start=").concat(String.valueOf(index)));
                        index = index + 8;
                }

        }

        public Observable<List<Article>> get() {
                return Observable
                        .create(new Observable.OnSubscribe<List<Article>>() {
                                @Override
                                public void call(final Subscriber<? super List<Article>> subscriber) {
                                        retrieveDocuments().observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<Document>() {
                                                        @Override
                                                        public void onCompleted() {

                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                                Log.e("Sea Retrieve ERR",e.toString());
                                                        }

                                                        @Override
                                                        public void onNext(Document document) {
                                                                ArrayList<Article> list = (ArrayList<Article>) new SeaParser().parse(document);
                                                                global_article_list.addAll(list);
                                                                subscriber.onNext(global_article_list);
                                                        }
                                                });
                                }
                        }).subscribeOn(Schedulers.io());
        }

        public Document getDocument(String urlRetrive) throws IOException {
                return Jsoup.connect(urlRetrive).timeout(10 * 1000).userAgent("Mozilla").get();
        }

        //Create an ArrayList contains urls of multiple pages
        private Observable<String> loadUrlList(){

            return Observable.from(urlsToRetrieve);
        }

        private Observable<Document> retrieveDocuments(){

                final List<Document> documents = new ArrayList<>();

                Subscription urlsubscription = loadUrlList().subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {}

                        @Override
                        public void onError(Throwable e) {
                                Log.e("Scienze Retrieve",e.toString());
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

                return Observable.from(documents).subscribeOn(Schedulers.io());

        }

}