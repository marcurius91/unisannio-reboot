package solutions.alterego.android.unisannio.ingegneria;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
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
import solutions.alterego.android.unisannio.DetailActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.utils.VHHeader;

public class IngegneriaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_ITEM = 1;

    private final Fragment mFragment;

    private List<IngegneriaDidatticaItem> mNewsList = new ArrayList<>();

    public IngegneriaAdapter(Fragment fragment, List<IngegneriaDidatticaItem> newsList) {
        mNewsList = newsList;
        mFragment = fragment;
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.ingengeria_card)
        CardView card;

        @InjectView(R.id.article_card_title)
        TextView title;

        @InjectView(R.id.article_card_author)
        TextView author;

        @InjectView(R.id.article_card_date)
        TextView date;

        private IngegneriaDidatticaItem mNews;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        void setItem(IngegneriaDidatticaItem news) {
            mNews = news;
            date.setText(news.getDate());
            title.setText(news.getTitle());

            String author = news.getAuthor();
            if (!"".equals(author)) {
                this.author.setText(author);
                this.author.setVisibility(View.VISIBLE);
            }
        }

        @OnClick(R.id.ingengeria_card)
        public void openDetailPage(View v) {
            Intent intent = new Intent();
            intent.setClass(v.getContext(), DetailActivity.class);
            intent.putExtra("ARTICLE", mNews);

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(mFragment.getActivity(),
                            Pair.create(title, v.getContext().getString(R.string.transition_article_title)),
                            Pair.create(date, v.getContext().getString(R.string.transition_article_date)),
                            Pair.create(author, v.getContext().getString(R.string.transition_article_author))
                    );
            ActivityCompat.startActivity(mFragment.getActivity(), intent, options.toBundle());
        }
    }
}