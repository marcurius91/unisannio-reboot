package solutions.alterego.android.unisannio.ingegneria;

import android.support.annotation.NonNull;
import android.util.Log;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.cercapersone.ICercapersonePresenter;
import solutions.alterego.android.unisannio.cercapersone.Person;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public class IngegneriaCercapersonePresenter implements ICercapersonePresenter {

    private IParser mParser;

    private IRetriever mRetriever;

    ArrayList<Person> persons = new ArrayList<>();

    @Override
    public void setParser(@NonNull IParser parser) {
        mParser = parser;
    }

    @Override
    public void setRetriever(@NonNull IRetriever retriever) {
        mRetriever = retriever;
    }

    @Override
    public Observable<ArrayList<Person>> getPeople() {

        IngegneriaCercapersoneRetriever icr = new IngegneriaCercapersoneRetriever();
        IngegneriaCercapersoneParser icp = (new IngegneriaCercapersoneParser());

        setRetriever(icr);
        setParser(icp);


        return Observable.
                create(new Observable.OnSubscribe<ArrayList<Person>>(){

                    @Override
                    public void call(final Subscriber<? super ArrayList<Person>> subscriber) {

                        mRetriever.retrieveDocument()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Document>() {

                                    @Override
                                    public void onCompleted() {
                                        //Log.e("OBSERVER RETRIVE DOCUMENT onCompleted()", "Completed without errors");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("CERCAPERSONE onError()", e.toString());

                                    }

                                    @Override
                                    public void onNext(Document document) {
                                        persons = (ArrayList<Person>) mParser.parse(document);
                                        subscriber.onNext(persons);

                                        /*for (int i = 0; i < persons.size(); i++) {
                                            Log.e("OBSERVER RETRIVE DOCUMENT onNext()", persons.get(i).getNome());
                                        }*/

                                    }
                                });
                    }
                }).subscribeOn(Schedulers.io());


    }
}
