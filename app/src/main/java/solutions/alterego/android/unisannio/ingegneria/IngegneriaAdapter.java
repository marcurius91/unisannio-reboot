package solutions.alterego.android.unisannio.ingegneria;

import org.joda.time.format.DateTimeFormat;
import org.parceler.Parcels;

import android.content.Context;
import android.content.Intent;
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
import solutions.alterego.android.unisannio.DetailActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.interfaces.StartActivityListener;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.VHHeader;

public class IngegneriaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_ITEM = 1;

    private final StartActivityListener mListener;

    private List<Article> mNewsList = new ArrayList<>();

    public IngegneriaAdapter(@NonNull StartActivityListener listener, @NonNull List<Article> newsList) {
        mNewsList = newsList;
        mListener = listener;
    }

    public void addNews(List<Article> newsList) {
        mNewsList.clear();
        mNewsList.add(null);
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(R.layout.article_card, viewGroup, false);
            return new ViewHolder(v);
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_header_image, viewGroup, false);
            return new VHHeader(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IngegneriaAdapter.ViewHolder) {
            final Article news = getItem(position);
            ((ViewHolder) holder).setItem(news);
        } else if (holder instanceof VHHeader) {
            ((VHHeader) holder).header.setImageResource(R.drawable.ding);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList == null ? 0 : mNewsList.size();
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

    private Article getItem(int position) {
        return mNewsList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.article_card)
        CardView card;

        @Bind(R.id.article_card_title)
        TextView title;

        @Bind(R.id.article_card_author)
        TextView author;

        @Bind(R.id.article_card_date)
        TextView date;

        private Article mNews;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void setItem(Article news) {
            mNews = news;
            String prettyDate = DateTimeFormat.forPattern("EEEE dd MMM YYYY").withLocale(Locale.ITALIAN).print(news.getDate());
            date.setText(prettyDate);
            title.setText(news.getTitle());

            String author = news.getAuthor();
            if (!"".equals(author)) {
                this.author.setText(author);
                this.author.setVisibility(View.VISIBLE);
            }
        }

        @OnClick(R.id.article_card)
        public void openDetailPage(View v) {
            Intent intent = new Intent();
            intent.setClass(v.getContext(), DetailActivity.class);
            intent.putExtra("ARTICLE", Parcels.wrap(mNews));
            mListener.startActivity(intent, this);
        }
    }
}