package solutions.alterego.android.unisannio.ateneo;

import android.app.Activity;
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

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.MainActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.models.ArticleAdapter;

public class AteneoAvvisiFragment extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.ateneo_ptr)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindColor(R.color.primaryColor)
    int mColorPrimary;

    @Inject
    AteneoRetriever mAteneoRetriever;

    private ArticleAdapter mAdapter;

    private boolean mIsStudenti;

    private CustomTabsHelperFragment mCustomTabsHelperFragment;

    private CustomTabsIntent mCustomTabsIntent;

    public static AteneoAvvisiFragment newInstance(boolean studenti) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("STUDENTI", studenti);

        AteneoAvvisiFragment fragment = new AteneoAvvisiFragment();
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

        int drawable = R.drawable.guerrazzi;

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.unisannio_yellow,
                R.color.unisannio_yellow_dark,
                R.color.unisannio_yellow_light,
                R.color.unisannio_blue);

        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Bundle bundle = getArguments();
        if (bundle != null) {
            mIsStudenti = bundle.getBoolean("STUDENTI");
        }

        mSwipeRefreshLayout.setOnRefreshListener(() -> refreshList(mIsStudenti));

        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this.getActivity());
        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();


        mAdapter = new ArticleAdapter(new ArrayList<>(), (article, holder) -> {
            String url1 = mIsStudenti ? URLS.ATENEO_DETAIL_STUDENTI_BASE_URL + article.getUrl() : URLS.ATENEO_DETAIL_BASE_URL + article.getUrl();
            CustomTabsHelperFragment.open(getActivity(), mCustomTabsIntent, Uri.parse(url1), mCustomTabsFallback);
        },R.drawable.guerrazzi);

        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        refreshList(mIsStudenti);
    }

    private void refreshList(boolean isStudenti) {
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        mAteneoRetriever.getNewsList(isStudenti)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onCompleted() {
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onNext(List<Article> ateneoNewsList) {
                        mAdapter.addNews(ateneoNewsList);
                        mRecyclerView.setVisibility(View.VISIBLE);
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
            new CustomTabsActivityHelper.CustomTabsFallback() {
                @Override
                public void openUri(Activity activity, Uri uri) {
                    Toast.makeText(activity, R.string.custom_tab_error, Toast.LENGTH_SHORT).show();
                    try {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, R.string.custom_tab_error_activity, Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            };


}
