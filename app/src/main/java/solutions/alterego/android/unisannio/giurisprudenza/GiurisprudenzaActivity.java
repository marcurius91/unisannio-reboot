package solutions.alterego.android.unisannio.giurisprudenza;

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
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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

public class GiurisprudenzaActivity extends NavigationDrawerActivity {

    RecyclerView mRecyclerView;
    int mColorPrimary;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CustomTabsHelperFragment mCustomTabsHelperFragment;

    private ArticleAdapter mAdapter;

    private GiurisprudenzaAvvisiPresenter mPresenter;

    private CustomTabsIntent mCustomTabsIntent;

    protected Intent mMap;

    public AnalyticsManager mAnalyticsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_giurisprudenza);
        mColorPrimary = getResources().getColor(R.color.primaryColor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mAnalyticsManager = new AnalyticsManager(this);

        mMap = new Intent(this, MapsActivity.class);
        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);
        
        mRecyclerView = (RecyclerView) findViewById(R.id.giurisprudenza_recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.giurisprudenza_swipe_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mPresenter = new GiurisprudenzaAvvisiPresenter();

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
                String url1 = URLS.GIURISPRUDENZA /*+ article.getUrl()*/;
                CustomTabsHelperFragment.open(GiurisprudenzaActivity.this, mCustomTabsIntent, Uri.parse(url1), mCustomTabsFallback);
            }
        }, R.drawable.calandra);

        refreshList();

        mRecyclerView.setAdapter(mAdapter);


    }

    private void refreshList() {
        //mRecyclerView.setVisibility(View.GONE);
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
                        Log.e("GIURISP ACTIVITY:", e.toString());
                    }

                    @Override
                    public void onNext(ArrayList<Article> articles) {
                        mAdapter.addNews(articles);
                        mSwipeRefreshLayout.setRefreshing(false);
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
                    mAnalyticsManager.track(new Screen(getString(R.string.giurisprudenza), getString(R.string.sito_web)));
                    CustomTabsHelperFragment.open(this, mCustomTabsIntent, Uri.parse(URLS.GIURISPRUDENZA), mCustomTabsFallback);
                    break;
                case R.id.action_map:
                    mAnalyticsManager.track(new Screen(getString(R.string.giurisprudenza), getString(R.string.mappa)));
                    mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.GIURISPRUDENZA()));
                    startActivity(mMap);
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        protected int getNavigationDrawerMenuIdForThisActivity () {
            return R.id.avvisi_studenti_giurisprudenza;
        }

        @Override
        protected void onAppbarNavigationClick () {
            openNavigationDrawer();
        }
    }
