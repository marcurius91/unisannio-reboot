package solutions.alterego.android.unisannio.ateneo;


import android.util.Log;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;
import solutions.alterego.android.unisannio.models.Article;

public class AteneoPresenter implements IAvvisiPresenter {

    private IParser<Article> mParser;

    private IRetriever<Document> mRetriever;

    ArrayList<Article> list = new ArrayList<>();

    public AteneoPresenter(String url){
        mParser = new AteneoAvvisiParser();
        mRetriever = new AteneoRetriever(url);
    }

    @Override
    public Observable<ArrayList<Article>> getArticles() {
        return Observable.
                create(new Observable.OnSubscribe<ArrayList<Article>>(){

                    @Override
                    public void call(final Subscriber<? super ArrayList<Article>> subscriber) {

                        mRetriever.retriveDocument()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Document>() {

                                    @Override
                                    public void onCompleted() {
                                        //Log.e("OBSERVER RETRIVE DOCUMENT onCompleted()", "Completed without errors");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("ATENEO PRES onError()", e.toString());

                                    }

                                    @Override
                                    public void onNext(Document document) {
                                        list = (ArrayList<Article>) mParser.parse(document);
                                        subscriber.onNext(list);
                                        /*for (int i = 0; i < list.size(); i++) {
                                            Log.e("OBSERVER onNext()", list.get(i).toString());
                                        }*/

                                    }
                                });
                    }
                }).subscribeOn(Schedulers.io());
    }
}
