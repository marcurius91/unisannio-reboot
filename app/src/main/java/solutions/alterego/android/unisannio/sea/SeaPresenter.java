package solutions.alterego.android.unisannio.sea;

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

public class SeaPresenter implements ISeaPresenter{

    private IParser<Article> mParser;

    private IRetriever<Document> mRetriever;


    ArrayList<Article> global_article_list = new ArrayList<>();

    public SeaPresenter(String url){
        mParser = new SeaParser();
        mRetriever = new SeaRetriever(url);
    }

    @Override
    public Observable<ArrayList<Article>> getArticles() {
        return Observable.
                create(new Observable.OnSubscribe<ArrayList<Article>>(){

                    @Override
                    public void call(final Subscriber<? super ArrayList<Article>> subscriber) {

                        mRetriever.retrieveDocument()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Document>() {

                                    @Override
                                    public void onCompleted() {
                                        //Log.e("OBSERVER RETRIVE DOCUMENT onCompleted()", "Completed without errors");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("SEA PRES onError()", e.toString());

                                    }

                                    @Override
                                    public void onNext(Document document) {

                                        //Log.e("Ateneo Presenter",document.toString());
                                        ArrayList<Article> list = (ArrayList<Article>) mParser.parse(document);
                                        global_article_list.addAll(list);
                                        subscriber.onNext(global_article_list);
                                        //for (int i = 0; i < list.size(); i++) {
                                        //    Log.e("OBSERVER onNext()", list.get(i).toString());
                                        //}


                                    }
                                });
                    }
                }).subscribeOn(Schedulers.io());
    }
}
