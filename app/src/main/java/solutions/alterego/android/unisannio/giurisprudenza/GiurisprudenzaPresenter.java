package solutions.alterego.android.unisannio.giurisprudenza;

import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;
import solutions.alterego.android.unisannio.models.Article;

public class GiurisprudenzaPresenter implements IGiurisprudenzaPresenter {

    private IParser mParser;
    private IRetriever mRetriver;
    private String urlToRetrive;
    ArrayList<Article> articles = new ArrayList<>();

    public GiurisprudenzaPresenter(String url){
        this.urlToRetrive = url;
    }

    @Override
    public void setParser(@NonNull IParser parser) {
        mParser = parser;
    }

    @Override
    public void setRetriever(@NonNull IRetriever retriever) { mRetriver = retriever; }

    @Override
    public Observable<ArrayList<Article>> getArticles() {

        GiurisprudenzaParser  gp = new GiurisprudenzaParser();
        GiurisprudenzaRetriever gr = new GiurisprudenzaRetriever(urlToRetrive);

        setParser(gp);
        setRetriever(gr);

        return Observable.
                create(new Observable.OnSubscribe<ArrayList<Article>>(){

                    @Override
                    public void call(Subscriber<? super ArrayList<Article>> subscriber) {

                        mRetriver.retriveDocument()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Document>() {

                                    @Override
                                    public void onCompleted() {
                                        //Log.e("OBSERVER RETRIVE DOCUMENT onCompleted()", "Completed without errors");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("GIRISPRUDENZA PRESENTER", e.toString());

                                    }

                                    @Override
                                    public void onNext(Document document) {
                                        articles = (ArrayList<Article>) mParser.parse(document);
                                        subscriber.onNext(articles);
                                    }
                                });
                    }
                }).subscribeOn(Schedulers.io());
    }


}
