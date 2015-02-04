package solutions.alterego.android.unisannio.ingegneria;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import solutions.alterego.android.unisannio.R;

public class IngegneriaAdapter extends RecyclerView.Adapter<IngegneriaAdapter.ViewHolder> {

    private List<IngegneriaDidatticaItem> mNewsList = new ArrayList<>();

    private int mRowLayout;

    private Context mContext;

    public IngegneriaAdapter(List<IngegneriaDidatticaItem> newsList, int rowLayout) {
        mNewsList = newsList;
        mRowLayout = rowLayout;
    }

    public void addNews(List<IngegneriaDidatticaItem> newsList) {
        mNewsList.clear();
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View v = LayoutInflater.from(mContext).inflate(mRowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final IngegneriaDidatticaItem news = mNewsList.get(i);
        viewHolder.setItem(news);
    }

    @Override
    public int getItemCount() {
        return mNewsList == null ? 0 : mNewsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.ingengeria_card)
        CardView card;

        @InjectView(R.id.ingegneria_news_body)
        TextView info;

        @InjectView(R.id.ingegneria_news_author)
        TextView author;

        @InjectView(R.id.ingegneria_news_date)
        TextView date;

        private IngegneriaDidatticaItem mNews;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        void setItem(IngegneriaDidatticaItem news) {
            mNews = news;
            date.setText(news.getDate());
            info.setText(news.getTitle());
            author.setText(news.getAuthor());
        }

        @OnClick(R.id.ingengeria_card)
        public void openDetailPage(View v) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mNews.getUrl()));
            v.getContext().startActivity(browserIntent);
        }
    }
}