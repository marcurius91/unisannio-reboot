package solutions.alterego.android.unisannio.interfaces;

import java.io.IOException;

import rx.Observable;

public interface IRetriever<T> {

    Observable<T> retriveDocument();
}
