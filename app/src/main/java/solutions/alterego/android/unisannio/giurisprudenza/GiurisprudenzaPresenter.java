package solutions.alterego.android.unisannio.giurisprudenza;

import android.util.Log;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.interfaces.Parser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;
import solutions.alterego.android.unisannio.models.Article;

public class GiurisprudenzaPresenter implements IGiurisprudenzaPresenter {

    /*private IParser<Article> mParser;

    private IRetriever<Document> mRetriver;

    private final GiurisprudenzaView view;

    public GiurisprudenzaPresenter(GiurisprudenzaView view, String url) {
        this.view = view;

        mParser = new GiurisprudenzaParser();
        mRetriver = new GiurisprudenzaRetriever(url);
    }

    @Override
    public void getArticles() {
        mRetriver.retrieveDocument()
            .map(document -> mParser.parse(document))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<Article>>() {
                @Override
                public void onCompleted() {
                    view.stopProgress();
                }

                @Override
                public void onError(Throwable e) {
                    view.stopProgress();
                }

                @Override
                public void onNext(List<Article> list) {
                    view.setArticles(list);
                }
            });
    }*/

    private Parser<Article> mParser;

    private IRetriever<Document> mRetriever;

    ArrayList<Article> list = new ArrayList<>();

    public GiurisprudenzaPresenter(String url) {

        mParser = new GiurisprudenzaParser();
        mRetriever = new GiurisprudenzaRetriever(url);
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
                                        Log.e("GIUR PRES onError()", e.toString());

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
