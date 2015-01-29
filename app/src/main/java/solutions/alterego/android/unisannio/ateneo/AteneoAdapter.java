package solutions.alterego.android.unisannio.ateneo;

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
import solutions.alterego.android.unisannio.URLS;

public class AteneoAdapter extends RecyclerView.Adapter<AteneoAdapter.ViewHolder> {

    private List<AteneoNews> mNewsList = new ArrayList<>();

    private int mRowLayout;

    private Context mContext;

    public AteneoAdapter(List<AteneoNews> newsList, int rowLayout) {
        mNewsList = newsList;
        mRowLayout = rowLayout;
    }

    public void addNews(List<AteneoNews> newsList) {
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
        final AteneoNews news = mNewsList.get(i);
        viewHolder.setItem(news);
    }

    @Override
    public int getItemCount() {
        return mNewsList == null ? 0 : mNewsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.ateneo_card)
        CardView card;

        @InjectView(R.id.ateneo_news_body)
        TextView info;

        @InjectView(R.id.ateneo_news_date)
        TextView date;

        private AteneoNews mNews;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        void setItem(AteneoNews news) {
            mNews = news;
            date.setText(news.getDate());
            info.setText(news.getBody());
        }

        @OnClick(R.id.ateneo_card)
        public void openDetailPage(View v) {
            String url = URLS.ATENEO_DETAIL_BASE_URL + mNews.getId();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            v.getContext().startActivity(browserIntent);
        }
    }
}