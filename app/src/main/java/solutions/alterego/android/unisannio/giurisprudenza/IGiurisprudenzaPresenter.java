package solutions.alterego.android.unisannio.giurisprudenza;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import rx.Observable;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.interfaces.IRetriever;
import solutions.alterego.android.unisannio.models.Article;

public interface IGiurisprudenzaPresenter {


    void setParser(@NonNull IParser parser);

    void setRetriever(@NonNull IRetriever retriever);

    Observable<ArrayList<Article>> getArticles();
}
