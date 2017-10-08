package solutions.alterego.android.unisannio.ateneo;

import java.util.List;
import rx.Observable;
import solutions.alterego.android.unisannio.models.Article;

public interface IAvvisiPresenter {

    Observable<List<Article>> getArticles();

}

