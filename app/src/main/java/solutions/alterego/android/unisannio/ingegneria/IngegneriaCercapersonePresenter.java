package solutions.alterego.android.unisannio.ingegneria;

import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.cercapersone.ICercapersonePresenter;
import solutions.alterego.android.unisannio.cercapersone.Person;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public class IngegneriaCercapersonePresenter implements ICercapersonePresenter {

    private IParser mParser;

    private IRetriever mRetriever;

    ArrayList<Person> persons;

    @Override
    public void setParser(@NonNull IParser parser) {
        mParser = parser;
    }

    @Override
    public void setRetriever(@NonNull IRetriever retriever) {
        mRetriever = retriever;
    }

    @Override
    public ArrayList<Person> getPeople() {

        IngegneriaCercapersoneRetriever icr = new IngegneriaCercapersoneRetriever();
        IngegneriaCercapersoneParser icp = (new IngegneriaCercapersoneParser());

        setRetriever(icr);
        setParser(icp);

            mRetriever.retriveDocument()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Document>() {

                    @Override
                    public void onCompleted() {
                        Log.e("OBSERVER RETRIVE DOCUMENT onCompleted()", "Completed without errors");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("OBSERVER RETRIVE DOCUMENT onError()", e.toString());

                    }

                    @Override
                    public void onNext(Document document) {
                        persons = (ArrayList<Person>) mParser.parse(document);
                        for (int i = 0; i < persons.size(); i++) {
                            Log.e("OBSERVER RETRIVE DOCUMENT onNext()", persons.get(i).getNome());
                        }

                    }
                });

        if(persons != null) {
            return persons;
        }
        else {
            Log.e("PRESENTER ERROR", "persons is empty");
            return null;
        }

    }
}
