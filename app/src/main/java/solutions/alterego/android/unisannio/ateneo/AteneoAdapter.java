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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.VHHeader;

public class AteneoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_ITEM = 1;

    private final boolean isStudenti;

    private List<Article> mNewsList = new ArrayList<>();

    private int mRowLayout;

    private Context mContext;

    public AteneoAdapter(List<Article> newsList, int rowLayout, boolean isStudenti) {
        mNewsList = newsList;
        mRowLayout = rowLayout;
        this.isStudenti = isStudenti;
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
            View v = LayoutInflater.from(context).inflate(R.layout.ateneo_card, viewGroup, false);
            return new ViewHolder(v, isStudenti);
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_header_image, viewGroup, false);
            return new VHHeader(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof AteneoAdapter.ViewHolder) {
            final Article news = mNewsList.get(i);
            ((ViewHolder) holder).setItem(news);
        } else if (holder instanceof VHHeader) {
            ((VHHeader) holder).header.setImageResource(R.drawable.guerrazzi);
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
    
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final boolean isStudenti;

        @Bind(R.id.ateneo_card)
        CardView card;

        @Bind(R.id.ateneo_news_body)
        TextView info;

        @Bind(R.id.ateneo_news_date)
        TextView date;

        private Article mNews;

        public ViewHolder(View view, boolean isStudenti) {
            super(view);
            ButterKnife.bind(this, view);
            this.isStudenti = isStudenti;
        }

        void setItem(Article news) {
            mNews = news;
            date.setText(news.getDate());
            info.setText(news.getTitle());
        }

        @OnClick(R.id.ateneo_card)
        public void openDetailPage(View v) {
            String url;
            if (isStudenti) {
                url = URLS.ATENEO_DETAIL_STUDENTI_BASE_URL + mNews.getUrl();
            } else {
                url = URLS.ATENEO_DETAIL_BASE_URL + mNews.getUrl();
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            v.getContext().startActivity(browserIntent);
        }
    }
}