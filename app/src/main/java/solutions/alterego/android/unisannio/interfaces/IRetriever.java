package solutions.alterego.android.unisannio.interfaces;

import rx.Observable;

public interface IRetriever<T> {
    Observable<T> retrieveDocument();
}
