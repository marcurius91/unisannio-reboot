package solutions.alterego.android.unisannio.ingegneria;

import java.util.List;

import rx.Observable;
import solutions.alterego.android.unisannio.models.Article;

public interface IngegneriaRetriever {

    abstract public Observable<List<Article>> get();
}
