package solutions.alterego.android.unisannio.ingegneria;

import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.nodes.Document;

import java.util.List;

import nl.matshofman.saxrssreader.FeedItem;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.cercapersone.ICercapersonePresenter;
import solutions.alterego.android.unisannio.cercapersone.Person;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public class IngegneriaCercapersonePresenter implements ICercapersonePresenter {

    private IParser mParser;

    private IRetriever mRetriever;

    public List<Person> persons;

    @Override
    public void setParser(@NonNull IParser parser) {
        mParser = parser;
    }

    @Override
    public void setRetriever(@NonNull IRetriever retriever) {
        mRetriever = retriever;
    }

    @Override
    public List<Person> getPeople() {

        IngegneriaCercapersoneRetriever icr = new IngegneriaCercapersoneRetriever();
        setRetriever(icr);
        IngegneriaCercapersoneParser icp = (new IngegneriaCercapersoneParser());
        setParser(icp);

        mRetriever.retrieve()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FeedItem>>() {
            @Override
            public void onCompleted() {
                Log.i("OBSERVER", "Completed");
            }

            @Override
            public void onError(Throwable e) {

                Log.e("OBSERVER ERROR", e.toString());
            }

            @Override
            public void onNext(List<FeedItem> feedItems) {

                Log.e("ON NEXT RESULT",feedItems.get(0).toString());
            }


        });

        return persons;
    }
}
