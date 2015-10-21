package solutions.alterego.android.unisannio.models;

import org.joda.time.format.DateTimeFormat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.interfaces.OpenArticleDetailListener;
import solutions.alterego.android.unisannio.utils.VHHeader;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_ITEM = 1;

    private final OpenArticleDetailListener mOpenArticleDetailListener;

    private List<Article> mArticleList = new ArrayList<>();

    public ArticleAdapter(@NonNull List<Article> articleList, @NonNull OpenArticleDetailListener openArticleDetailListener) {
        mArticleList = articleList;
        mOpenArticleDetailListener = openArticleDetailListener;
    }

    public void addNews(List<Article> articleList) {
        mArticleList.clear();
        mArticleList.add(null);
        mArticleList.addAll(articleList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(R.layout.article_card, viewGroup, false);
            return new ViewHolder(v, mOpenArticleDetailListener);
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_header_image, viewGroup, false);
            return new VHHeader(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof ArticleAdapter.ViewHolder) {
            final Article news = mArticleList.get(i);
            ((ViewHolder) holder).setItem(news);
        } else if (holder instanceof VHHeader) {
            ((VHHeader) holder).header.setImageResource(R.drawable.sea);
        }
    }

    @Override
    public int getItemCount() {
        return mArticleList == null ? 0 : mArticleList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final OpenArticleDetailListener mOpenArticleDetailListener;

        @Bind(R.id.article_card)
        CardView card;

        @Bind(R.id.article_card_title)
        TextView body;

        @Bind(R.id.article_card_date)
        TextView date;

        private Article mArticle;

        public ViewHolder(View view, @NonNull OpenArticleDetailListener openArticleDetailListener) {
            super(view);
            ButterKnife.bind(this, view);
            mOpenArticleDetailListener = openArticleDetailListener;
        }

        void setItem(Article news) {
            mArticle = news;
            String prettyDate = DateTimeFormat.forPattern("EEEE dd MMM YYYY").withLocale(Locale.ITALIAN).print(news.getDate());
            date.setText(prettyDate);
            body.setText(news.getTitle());
        }

        @OnClick(R.id.article_card)
        public void openDetailPage(View v) {
            mOpenArticleDetailListener.openArticleDetail(mArticle, this);
        }
    }
}