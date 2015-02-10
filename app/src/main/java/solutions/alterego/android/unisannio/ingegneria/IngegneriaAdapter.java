package solutions.alterego.android.unisannio.ingegneria;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.utils.VHHeader;

public class IngegneriaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_ITEM = 1;

    private List<IngegneriaDidatticaItem> mNewsList = new ArrayList<>();

    public IngegneriaAdapter(List<IngegneriaDidatticaItem> newsList) {
        mNewsList = newsList;
    }

    public void addNews(List<IngegneriaDidatticaItem> newsList) {
        mNewsList.clear();
        mNewsList.add(null);
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(R.layout.ingegneria_card, viewGroup, false);
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
            final IngegneriaDidatticaItem news = getItem(position);
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

    private IngegneriaDidatticaItem getItem(int position) {
        return mNewsList.get(position);
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
            String url = mNews.getUrl();
            if (!"".equals(url)) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                v.getContext().startActivity(browserIntent);
            }
        }
    }
}