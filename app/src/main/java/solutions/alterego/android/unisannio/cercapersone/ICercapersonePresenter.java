package solutions.alterego.android.unisannio.cercapersone;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import rx.Observable;
import solutions.alterego.android.unisannio.interfaces.Parser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;

public interface ICercapersonePresenter {

    void setParser(@NonNull Parser parser);

    void setRetriever(@NonNull IRetriever retriever);

    Observable<ArrayList<Person>> getPeople();
}
