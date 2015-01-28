package solutions.alterego.android.unisannio.ateneo;

import android.content.Context;
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
import solutions.alterego.android.unisannio.R;

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
        notifyItemRangeInserted(0, newsList.size() - 1);
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
        viewHolder.date.setText(news.getDate());
        viewHolder.info.setText(news.getBody());
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}