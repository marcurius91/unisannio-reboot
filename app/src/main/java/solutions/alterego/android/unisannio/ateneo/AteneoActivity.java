//prova commit da android studio su git enrico 7 marzo 2018
package solutions.alterego.android.unisannio.ateneo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.MapsActivity;
import solutions.alterego.android.unisannio.NavigationDrawerActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.analytics.AnalyticsManager;
import solutions.alterego.android.unisannio.analytics.Screen;
import solutions.alterego.android.unisannio.interfaces.OpenArticleDetailListener;
import solutions.alterego.android.unisannio.map.UnisannioGeoData;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.models.ArticleAdapter;
import solutions.alterego.android.unisannio.runtimePermission.PermissionManager;
import timber.log.Timber;

public class AteneoActivity extends NavigationDrawerActivity {

    @Inject
    AnalyticsManager mAnalyticsManager;

    RecyclerView mRecyclerView;
    int mColorPrimary;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CustomTabsHelperFragment mCustomTabsHelperFragment;

    private ArticleAdapter mAdapter;

    private CustomTabsIntent mCustomTabsIntent;

    private AteneoPresenter mPresenter;

    protected Intent mMap;

    PermissionManager mPermissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.component(this).inject(this);

        setContentView(R.layout.activity_new_ateneo);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.ateneo_ptr);
        mColorPrimary = getResources().getColor(R.color.primaryColor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        mMap = new Intent(this, MapsActivity.class);
        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);

        mPresenter = new AteneoPresenter(URLS.ATENEO_NEWS);

        mRecyclerView = (RecyclerView) findViewById(R.id.ateneo_recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.ateneo_swipe_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryColor));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));


        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();

        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);

        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter = new ArticleAdapter(new ArrayList<Article>(), new OpenArticleDetailListener() {
            @Override public void openArticleDetail(@NonNull Article article, @NonNull RecyclerView.ViewHolder holder) {
                String url1 = /*URLS.ATENEO_DETAIL_STUDENTI_BASE_URL + article.getUrl():*/ URLS.ATENEO_DETAIL_BASE_URL + article.getUrl();
                CustomTabsHelperFragment.open(AteneoActivity.this, mCustomTabsIntent, Uri.parse(url1), mCustomTabsFallback);
            }
        },R.drawable.guerrazzi);

        refreshList();

        mRecyclerView.setAdapter(mAdapter);


    }

    private void refreshList() {
        //cercapersone_ingegneria_recycle_view.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getArticles()
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.addNews(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        switch (id) {
            case R.id.action_web_page:
                mAnalyticsManager.track(new Screen(getString(R.string.ateneo), getString(R.string.sito_web)));
                CustomTabsHelperFragment.open(this, mCustomTabsIntent, Uri.parse(URLS.ATENEO), mCustomTabsFallback);
                break;
            case R.id.action_map:
                mAnalyticsManager.track(new Screen(getString(R.string.ateneo), getString(R.string.mappa)));
                mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.ATENEO()));
                startActivity(mMap);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getNavigationDrawerMenuIdForThisActivity () {
        return R.id.drawer_ateneo_avvisi;
    }

    @Override
    protected void onAppbarNavigationClick () {
        openNavigationDrawer();
    }
}
