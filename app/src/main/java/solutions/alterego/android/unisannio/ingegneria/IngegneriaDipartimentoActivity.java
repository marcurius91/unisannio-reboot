package solutions.alterego.android.unisannio.ingegneria;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import org.chromium.customtabsclient.CustomTabsActivityHelper;
import org.parceler.Parcels;
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

public class IngegneriaDipartimentoActivity extends NavigationDrawerActivity {


    @Inject
    AnalyticsManager mAnalyticsManager;

    @Inject IngegneriaAvvisiDipartimentoRetriever mRetriever;

    RecyclerView mRecyclerView;
    int mColorPrimary;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArticleAdapter mAdapter;

    protected Intent mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.component(this).inject(this);

        setContentView(R.layout.activity_ingegneria_dipartimento);
        mColorPrimary = getResources().getColor(R.color.primaryColor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mMap = new Intent(this, MapsActivity.class);
        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();

        mRecyclerView = (RecyclerView) findViewById(R.id.ingegneria_dipartimento_recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.ingegneria_dipartimento_swipe_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryColor));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);

        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter = new ArticleAdapter(new ArrayList<Article>(), new OpenArticleDetailListener() {
            @Override public void openArticleDetail(@NonNull Article article, @NonNull RecyclerView.ViewHolder holder) {
                Intent intent = new Intent();
                intent.setClass(IngegneriaDipartimentoActivity.this, DetailActivity.class);
                intent.putExtra("ARTICLE", Parcels.wrap(article));

            /*ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            Pair.create(((ArticleAdapter.ViewHolder) holder).title, getString(R.string.transition_article_title)),
                            Pair.create(((ArticleAdapter.ViewHolder) holder).date, getString(R.string.transition_article_date)),
                            Pair.create(((ArticleAdapter.ViewHolder) holder).author, getString(R.string.transition_article_author))
                    );
            ActivityCompat.startActivity(this, intent, options.toBundle());*/
                IngegneriaDipartimentoActivity.this.startActivity(intent);
            }
        }, R.drawable.ding);

        refreshList();

        mRecyclerView.setAdapter(mAdapter);
    }

    private void refreshList() {
        mRecyclerView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        mRetriever.get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onCompleted() {
                        if (mRecyclerView != null && mSwipeRefreshLayout != null) {
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onNext(List<Article> list) {
                        mAdapter.addNews(list);
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
                mAnalyticsManager.track(new Screen(getString(R.string.ingegneria), getString(R.string.sito_web)));
                CustomTabsHelperFragment.open(this, mCustomTabsIntent, Uri.parse(URLS.INGEGNERIA), mCustomTabsFallback);
                break;
            case R.id.action_map:
                mAnalyticsManager.track(new Screen(getString(R.string.giurisprudenza), getString(R.string.mappa)));
                mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.INGEGNERIA()));
                startActivity(mMap);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getNavigationDrawerMenuIdForThisActivity() {
        return R.id.drawer_ingegneria_avvisi_dipartimento;
    }

    @Override
    protected void onAppbarNavigationClick () {
        openNavigationDrawer();
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
