package solutions.alterego.android.unisannio.scienze;

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
import java.util.List;
import java.util.UUID;
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
import solutions.alterego.android.unisannio.interfaces.OpenArticleDetailListener;
import solutions.alterego.android.unisannio.map.UnisannioGeoData;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.models.ArticleAdapter;
import timber.log.Timber;

public class ScienzeActivity extends NavigationDrawerActivity {

    @Inject AnalyticsManager mAnalyticsManager;

    @Inject ScienzeRetriever mRetriever;

    RecyclerView mRecyclerView;
    int mColorPrimary;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArticleAdapter mAdapter;

    ScienzeDetailPresenter mPresenter;

    protected Intent mMap;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        App.component(this).inject(this);

        setContentView(R.layout.activity_scienze);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.ateneo_ptr);
        mColorPrimary = getResources().getColor(R.color.unisannio_yellow);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mMap = new Intent(this, MapsActivity.class);

        mCustomTabsIntent = new CustomTabsIntent.Builder().enableUrlBarHiding().setToolbarColor(mColorPrimary).setShowTitle(true).build();

        mRecyclerView = (RecyclerView) findViewById(R.id.scienze_recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.scienze_swipe_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.unisannio_yellow));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);

        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter = new ArticleAdapter(new ArrayList<Article>(), new OpenArticleDetailListener() {
            @Override public void openArticleDetail(@NonNull final Article article, @NonNull RecyclerView.ViewHolder holder) {

                String url1 = article.getUrl();

                mPresenter = new ScienzeDetailPresenter(url1);

                mPresenter.getBodyNews().observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArrayList<String>>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {
                        Log.e("Error presenter", e.toString());
                    }

                    @Override public void onNext(ArrayList<String> strings) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), DetailActivity.class);
                        intent.putExtra("ARTICLE",
                            article.copy(UUID.randomUUID().toString(), article.getTitle(), article.getAuthor(), article.getUrl(), strings.get(0),
                                article.getDate()));
                        startActivity(intent);
                    }
                });
            /*ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            Pair.create(((ArticleAdapter.ViewHolder) holder).title, getString(R.string.transition_article_title)),
                            Pair.create(((ArticleAdapter.ViewHolder) holder).date, getString(R.string.transition_article_date)),
                            Pair.create(((ArticleAdapter.ViewHolder) holder).author, getString(R.string.transition_article_author))
                    );
            ActivityCompat.startActivity(this, intent, options.toBundle());*/

            }
        }, R.drawable.scienze);

        refreshList();

        mRecyclerView.setAdapter(mAdapter);
    }

    private void refreshList() {
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        mRetriever.get().observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Article>>() {
            @Override public void onCompleted() {
                        /* (cercapersone_ingegneria_recycle_view != null && cercapersone_ingegneria_swipe_container != null) {
                            cercapersone_ingegneria_recycle_view.setVisibility(View.VISIBLE);
                            cercapersone_ingegneria_swipe_container.setRefreshing(false);
                        }*/
            }

            @Override public void onError(Throwable e) {
                Timber.e(e);
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override public void onNext(List<Article> list) {
                mAdapter.addNews(list);
                if (mRecyclerView != null && mSwipeRefreshLayout != null) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_web_page:
                mAnalyticsManager.track(new Screen(getString(R.string.scienze), getString(R.string.sito_web)));
                CustomTabsHelperFragment.open(this, mCustomTabsIntent, Uri.parse(URLS.SCIENZE), mCustomTabsFallback);
                break;
            case R.id.action_map:
                mAnalyticsManager.track(new Screen(getString(R.string.scienze), getString(R.string.mappa)));
                mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SCIENZE()));
                startActivity(mMap);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override protected int getNavigationDrawerMenuIdForThisActivity() {
        return R.id.avvisi_studenti_scienze_tecnologie;
    }

    @Override protected void onAppbarNavigationClick() {
        openNavigationDrawer();
    }
}
