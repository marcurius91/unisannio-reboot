package solutions.alterego.android.unisannio.sea;

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
import javax.inject.Inject;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.DetailActivity;
import solutions.alterego.android.unisannio.MapsActivity;
import solutions.alterego.android.unisannio.NavigationDrawerActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.analytics.AnalyticsManager;
import solutions.alterego.android.unisannio.analytics.Screen;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaDipartimentoActivity;
import solutions.alterego.android.unisannio.interfaces.OpenArticleDetailListener;
import solutions.alterego.android.unisannio.map.UnisannioGeoData;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.models.ArticleAdapter;

public class SeaActivity extends NavigationDrawerActivity {

    @Inject
    AnalyticsManager mAnalyticsManager;

    int mColorPrimary;

    private CustomTabsHelperFragment mCustomTabsHelperFragment;

    private ArticleAdapter mAdapter;

    private CustomTabsIntent mCustomTabsIntent;

    private SeaPresenter mPresenter;


    public String url1=null;


    protected Intent mMap;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.component(this).inject(this);

        setContentView(R.layout.activity_sea);
        mColorPrimary = getResources().getColor(R.color.primaryColor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mMap = new Intent(this, MapsActivity.class);

        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);


        mPresenter = new SeaPresenter(URLS.SEA_NEWS);


        mRecyclerView = (RecyclerView) findViewById(R.id.sea_recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sea_swipe_container);
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


                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), DetailActivity.class);
                intent.putExtra("ARTICLE", article);

            /*ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            Pair.create(((ArticleAdapter.ViewHolder) holder).title, getString(R.string.transition_article_title)),
                            Pair.create(((ArticleAdapter.ViewHolder) holder).date, getString(R.string.transition_article_date)),
                            Pair.create(((ArticleAdapter.ViewHolder) holder).author, getString(R.string.transition_article_author))
                    );
            ActivityCompat.startActivity(this, intent, options.toBundle());*/

                SeaActivity.this.startActivity(intent);

                //   String url1 = URLS.SEA_NEWS;
               //CustomTabsHelperFragment.open(SeaActivity.this, mCustomTabsIntent, Uri.parse(url1), mCustomTabsFallback);







            }
        },R.drawable.sea);


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
                        Log.e("SEA ACTIVITY:", e.toString());
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
                mAnalyticsManager.track(new Screen(getString(R.string.sea), getString(R.string.sito_web)));
                CustomTabsHelperFragment.open(this, mCustomTabsIntent, Uri.parse(URLS.SEA), mCustomTabsFallback);
                break;
            case R.id.action_map:
                mAnalyticsManager.track(new Screen(getString(R.string.sea), getString(R.string.mappa)));
                mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SEA()));
                startActivity(mMap);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getNavigationDrawerMenuIdForThisActivity() {
        return R.id.drawer_section_sea;
    }

    @Override
    protected void onAppbarNavigationClick () {
        openNavigationDrawer();
    }
}
