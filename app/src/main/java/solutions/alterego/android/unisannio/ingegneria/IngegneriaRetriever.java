package solutions.alterego.android.unisannio.ingegneria;

import java.util.List;

import rx.Observable;

public interface IngegneriaRetriever {

    abstract public Observable<List<IngegneriaDidatticaItem>> get();
}
