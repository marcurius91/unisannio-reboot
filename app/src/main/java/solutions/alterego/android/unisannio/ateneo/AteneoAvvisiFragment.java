package solutions.alterego.android.unisannio.ateneo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.OpenArticleDetailListener;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.models.ArticleAdapter;

public class AteneoAvvisiFragment extends Fragment {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int mColorPrimary;

    @Inject AteneoRetriever mAteneoRetriever;

    private ArticleAdapter mAdapter;

    private boolean mIsStudenti;

    private CustomTabsIntent mCustomTabsIntent;

    private AteneoPresenter mPresenter;

    public static AteneoAvvisiFragment newInstance(boolean studenti) {
        //Bundle bundle = new Bundle();
        //bundle.putBoolean("STUDENTI", studenti);

        AteneoAvvisiFragment fragment = new AteneoAvvisiFragment();
        //fragment.setArguments(bundle);
        return fragment;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ateneo_ptr);
        mColorPrimary = getResources().getColor(R.color.primaryColor);

        int drawable = R.drawable.guerrazzi;

        mSwipeRefreshLayout.setColorSchemeResources(R.color.primaryColor, R.color.primaryDarkColor, R.color.primatyLightColor,
            R.color.unisannio_blue);

        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mPresenter = new AteneoPresenter(URLS.ATENEO_NEWS);

        //Bundle bundle = getArguments();
        /*if (bundle != null) {
            mIsStudenti = bundle.getBoolean("STUDENTI");
        }*/

        mIsStudenti = true;

        //cercapersone_ingegneria_swipe_container.setOnRefreshListener(() -> refreshList(mIsStudenti));

        CustomTabsHelperFragment mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this.getActivity());
        mCustomTabsIntent = new CustomTabsIntent.Builder().enableUrlBarHiding().setToolbarColor(mColorPrimary).setShowTitle(true).build();

        mAdapter = new ArticleAdapter(new ArrayList<Article>(), new OpenArticleDetailListener() {
            @Override public void openArticleDetail(@NonNull Article article, @NonNull RecyclerView.ViewHolder holder) {
                String url1 = mIsStudenti ? URLS.ATENEO_DETAIL_STUDENTI_BASE_URL + article.getUrl() : URLS.ATENEO_DETAIL_BASE_URL + article.getUrl();
                CustomTabsHelperFragment.open(AteneoAvvisiFragment.this.getActivity(), mCustomTabsIntent, Uri.parse(url1), mCustomTabsFallback);
            }
        }, R.drawable.guerrazzi);

        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        refreshList();
    }

    /*private void refreshList(boolean isStudenti) {
        cercapersone_ingegneria_recycle_view.setVisibility(View.GONE);
        cercapersone_ingegneria_swipe_container.setRefreshing(true);

        mAteneoRetriever.getNewsList(isStudenti)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onCompleted() {
                        if (cercapersone_ingegneria_swipe_container != null) {
                            cercapersone_ingegneria_swipe_container.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (cercapersone_ingegneria_swipe_container != null) {
                            cercapersone_ingegneria_swipe_container.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onNext(List<Article> ateneoNewsList) {
                        mAdapter.addNews(ateneoNewsList);
                        cercapersone_ingegneria_recycle_view.setVisibility(View.VISIBLE);
                    }
                });
    }*/

    private void refreshList() {
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getArticles().observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Article>>() {
            @Override public void onCompleted() {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override public void onError(Throwable e) {

            }

            @Override public void onNext(List<Article> articles) {
                mAdapter.addNews(articles);
            }
        });
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        App.component(context).inject(this);
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
