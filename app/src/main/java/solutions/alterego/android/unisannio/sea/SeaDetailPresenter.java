package solutions.alterego.android.unisannio.sea;

import android.util.Log;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SeaDetailPresenter{

        private SeaDetailParser mParser;
        private SeaDetailRetriever mRetriver;
        String urlToRetrive;
        ArrayList<String> mBody = new ArrayList<>();

        public SeaDetailPresenter(String url){
            this.urlToRetrive = url;
            mParser = new SeaDetailParser();
            mRetriver = new SeaDetailRetriever(url);
        }

        public Observable<ArrayList<String>> getBodyNews(){

            return Observable.
                    create(new Observable.OnSubscribe<ArrayList<String>>() {

                        @Override
                        public void call(final Subscriber<? super ArrayList<String>> subscriber) {

                            mRetriver.retrieveDocument()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<Document>() {

                                        @Override
                                        public void onCompleted() {
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e("SEADETAIL onError()", e.toString());
                                        }

                                        @Override
                                        public void onNext(Document document) {
                                            mBody = (ArrayList<String>) mParser.parse(document);
                                            subscriber.onNext(mBody);
                                        }
                                    });
                        }
                    }).subscribeOn(Schedulers.io());
        }
}
