package solutions.alterego.android.unisannio.ingegneria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.DetailActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.interfaces.OpenArticleDetailListener;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.models.ArticleAdapter;
import timber.log.Timber;

public class IngegneriaAvvisiFragment extends Fragment {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    IngegneriaRetriever mRetriever;

    private ArticleAdapter mAdapter;

    public static IngegneriaAvvisiFragment newInstance(boolean isDipartimento) {
        //Bundle bundle = new Bundle();
        //bundle.putBoolean("DIPARTIMENTO", isDipartimento);

        IngegneriaAvvisiFragment fragment = new IngegneriaAvvisiFragment();
        //fragment.setArguments(bundle);
        return fragment;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingegneria, container, false);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ingegneria_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ingengeria_ptr);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.ateneoColor, R.color.primaryDarkColor, R.color.primatyLightColor,
            R.color.unisannio_blue);

        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //Bundle bundle = getArguments();
        //if (bundle != null) {
        boolean isDipartimento = /*bundle.getBoolean("DIPARTIMENTO");*/ true;

        if (isDipartimento) {
            mRetriever = new IngegneriaAvvisiDipartimentoRetriever();
        } else {
            mRetriever = new IngegneriaAvvisiStudentiRetriever();
        }
        //}

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                refreshList();
            }
        });

        mAdapter = new ArticleAdapter(new ArrayList<Article>(), new OpenArticleDetailListener() {
            @Override public void openArticleDetail(@NonNull Article article, @NonNull RecyclerView.ViewHolder holder) {
                Intent intent = new Intent();
                intent.setClass(IngegneriaAvvisiFragment.this.getActivity(), DetailActivity.class);
                intent.putExtra("ARTICLE", article);

            /*ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            Pair.create(((ArticleAdapter.ViewHolder) holder).title, getString(R.string.transition_article_title)),
                            Pair.create(((ArticleAdapter.ViewHolder) holder).date, getString(R.string.transition_article_date)),
                            Pair.create(((ArticleAdapter.ViewHolder) holder).author, getString(R.string.transition_article_author))
                    );*/
                //ActivityCompat.startActivity(getActivity(), intent,options.toBundle());
                IngegneriaAvvisiFragment.this.startActivity(intent);
            }
        }, R.drawable.ding);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        refreshList();
    }

    private void refreshList() {
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        mRetriever.get().observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Article>>() {
            @Override public void onCompleted() {
                if (mRecyclerView != null && mSwipeRefreshLayout != null) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override public void onError(Throwable e) {
                Timber.e(e);
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override public void onNext(List<Article> list) {
                mAdapter.addNews(list);
            }
        });
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        App.component(context).inject(this);
    }
}
