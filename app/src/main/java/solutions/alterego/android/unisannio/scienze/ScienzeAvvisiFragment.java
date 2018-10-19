package solutions.alterego.android.unisannio.scienze;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.DetailActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.OpenArticleDetailListener;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.models.ArticleAdapter;
import timber.log.Timber;

public class ScienzeAvvisiFragment extends Fragment {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int mColorPrimary;

    private ArticleAdapter mAdapter;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ateneo_ptr);
        mColorPrimary = getResources().getColor(R.color.unisannio_yellow);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.ateneoColor, R.color.primaryDarkColor, R.color.primatyLightColor,
            R.color.unisannio_blue);

        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final String url = URLS.SCIENZE_NEWS;

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                ScienzeAvvisiFragment.this.refreshList(url);
            }
        });

        CustomTabsHelperFragment mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this.getActivity());
        CustomTabsIntent mCustomTabsIntent = new CustomTabsIntent.Builder().enableUrlBarHiding().setToolbarColor(mColorPrimary).setShowTitle(true).build();

        mAdapter = new ArticleAdapter(new ArrayList<Article>(), new OpenArticleDetailListener() {
            @Override public void openArticleDetail(@NonNull final Article article, @NonNull RecyclerView.ViewHolder holder) {
                String url1 = URLS.SCIENZE_NEWS + article.getUrl();
                //CustomTabsHelperFragment.open(getActivity(), mCustomTabsIntent, Uri.parse(url1), mCustomTabsFallback);
                ScienzeDetailPresenter mDetailPresenter = new ScienzeDetailPresenter(url1);

                mDetailPresenter.getBodyNews().observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArrayList<String>>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {
                        Log.e("getBodyNews", e.toString());
                    }

                    @Override public void onNext(ArrayList<String> bodys) {
                        //article.setBody(bodys.get(0));
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), DetailActivity.class);
                        intent.putExtra("ARTICLE", article);
                                       /*ActivityOptionsCompat options =
                                               ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                                       Pair.create(((ArticleAdapter.ViewHolder) holder).title, getString(R.string.transition_article_title)),
                                                       Pair.create(((ArticleAdapter.ViewHolder) holder).date, getString(R.string.transition_article_date)),
                                                       Pair.create(((ArticleAdapter.ViewHolder) holder).author, getString(R.string.transition_article_author))
                                               );
                                       ActivityCompat.startActivity(getActivity(), intent, options.toBundle());*/
                        startActivity(intent);
                    }
                });
            }
        }, R.drawable.scienze);

        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        refreshList(url);
    }

    private void refreshList(String url) {
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        ScienzeRetriever sr = new ScienzeRetriever(url);

        sr.get().observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Article>>() {
            @Override public void onCompleted() {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override public void onError(Throwable e) {
                Timber.e(e);
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override public void onNext(List<Article> ateneoNewses) {
                mAdapter.addNews(ateneoNewses);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        App.component(activity).inject(this);
    }

    private final CustomTabsActivityHelper.CustomTabsFallback mCustomTabsFallback = new CustomTabsActivityHelper.CustomTabsFallback() {
        @Override public void openUri(Activity activity, Uri uri) {
            Toast.makeText(activity, R.string.custom_tab_error, Toast.LENGTH_SHORT).show();
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(activity, R.string.custom_tab_error_activity, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
