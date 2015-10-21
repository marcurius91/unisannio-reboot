package solutions.alterego.android.unisannio.interfaces;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import solutions.alterego.android.unisannio.models.Article;

public interface OpenArticleDetailListener {

    void openArticleDetail(@NonNull Article article, @NonNull RecyclerView.ViewHolder holder);

}
