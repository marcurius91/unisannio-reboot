package solutions.alterego.android.unisannio.cercapersone;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import rx.Observable;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public interface ICercapersonePresenter {

    public void setParser(@NonNull IParser parser);

    public void setRetriever(@NonNull IRetriever retriever);

    public Observable<ArrayList<Person>> getPeople();
}
