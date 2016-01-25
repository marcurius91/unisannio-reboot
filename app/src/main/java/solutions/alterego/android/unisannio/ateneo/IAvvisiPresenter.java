package solutions.alterego.android.unisannio.ateneo;

import java.util.ArrayList;

import rx.Observable;
import solutions.alterego.android.unisannio.models.Article;

public interface IAvvisiPresenter {

    Observable<ArrayList<Article>> getArticles();

}

