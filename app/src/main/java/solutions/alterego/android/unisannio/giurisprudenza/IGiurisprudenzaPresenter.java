package solutions.alterego.android.unisannio.giurisprudenza;

import java.util.ArrayList;

import rx.Observable;
import solutions.alterego.android.unisannio.models.Article;

interface IGiurisprudenzaPresenter {

    Observable<ArrayList<Article>> getArticles();
}
