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
import solutions.alterego.android.unisannio.utils.VHHeader;

public class AteneoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_ITEM = 1;

    private final boolean isStudenti;

    private List<AteneoNews> mNewsList = new ArrayList<>();

    private int mRowLayout;

    private Context mContext;

    public AteneoAdapter(List<AteneoNews> newsList, int rowLayout, boolean isStudenti) {
        mNewsList = newsList;
        mRowLayout = rowLayout;
        this.isStudenti = isStudenti;
    }

    public void addNews(List<AteneoNews> newsList) {
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
            final AteneoNews news = mNewsList.get(i);
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

        @InjectView(R.id.ateneo_card)
        CardView card;

        @InjectView(R.id.ateneo_news_body)
        TextView info;

        @InjectView(R.id.ateneo_news_date)
        TextView date;

        private AteneoNews mNews;

        public ViewHolder(View view, boolean isStudenti) {
            super(view);
            ButterKnife.inject(this, view);
            this.isStudenti = isStudenti;
        }

        void setItem(AteneoNews news) {
            mNews = news;
            date.setText(news.getDate());
            info.setText(news.getBody());
        }

        @OnClick(R.id.ateneo_card)
        public void openDetailPage(View v) {
            String url;
            if (isStudenti) {
                url = URLS.ATENEO_DETAIL_STUDENTI_BASE_URL + mNews.getId();
            } else {
                url = URLS.ATENEO_DETAIL_BASE_URL + mNews.getId();
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            v.getContext().startActivity(browserIntent);
        }
    }
}