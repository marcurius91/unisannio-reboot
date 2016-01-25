package solutions.alterego.android.unisannio.giurisprudenza;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.models.ArticleAdapter;

public class GiurisprudenzaAvvisiFragment extends Fragment implements GiurisprudenzaView {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.ateneo_ptr)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindColor(R.color.primaryColor)
    int mColorPrimary;

    private ArticleAdapter mAdapter;

    private GiurisprudenzaPresenter mPresenter;

    private CustomTabsHelperFragment mCustomTabsHelperFragment;

    private CustomTabsIntent mCustomTabsIntent;

    public static Fragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);

        GiurisprudenzaAvvisiFragment fragment = new GiurisprudenzaAvvisiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        //Bundle bundle = getArguments();
        //String url = bundle.getString("URL");

        mPresenter = new GiurisprudenzaPresenter(URLS.GIURISPRUDENZA_AVVISI);

        mSwipeRefreshLayout.setColorSchemeResources(
            R.color.unisannio_yellow,
            R.color.unisannio_yellow_dark,
            R.color.unisannio_yellow_light,
            R.color.unisannio_blue);

        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mSwipeRefreshLayout.setOnRefreshListener(this::refreshList);

        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this.getActivity());
        mCustomTabsIntent = new CustomTabsIntent.Builder()
            .enableUrlBarHiding()
            .setToolbarColor(mColorPrimary)
            .setShowTitle(true)
            .build();

        mAdapter = new ArticleAdapter(new ArrayList<>(), (article, holder) -> {
            String url1 = URLS.GIURISPRUDENZA + article.getUrl();
            CustomTabsHelperFragment.open(getActivity(), mCustomTabsIntent, Uri.parse(url1), mCustomTabsFallback);
        }, R.drawable.calandra);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        refreshList();
    }

    private void refreshList() {
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Article>>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Article> articles) {
                        mAdapter.addNews(articles);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App.component(context).inject(this);
    }

    private final CustomTabsActivityHelper.CustomTabsFallback mCustomTabsFallback =
        (activity, uri) -> {
            Toast.makeText(activity, R.string.custom_tab_error, Toast.LENGTH_SHORT).show();
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(activity, R.string.custom_tab_error_activity, Toast.LENGTH_SHORT)
                    .show();
            }
        };

    @Override
    public void setArticles(List<Article> articles) {
        mAdapter.addNews(articles);
        mRecyclerView.setVisibility(View.VISIBLE);
        stopProgress();
    }

    @Override
    public void stopProgress() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
