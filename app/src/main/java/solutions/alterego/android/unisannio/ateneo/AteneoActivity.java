
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.MainActivity;
import solutions.alterego.android.unisannio.MapsActivity;
import solutions.alterego.android.unisannio.NavigationDrawerActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.analytics.AnalyticsManager;
import solutions.alterego.android.unisannio.analytics.Screen;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaAvvisiFragment;
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

    String ateneo[];
    String ingegneria[];
    String scienze[];
    String giurisprudenza[];
    String sea[];

    PermissionManager mPermissionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.component(this).inject(this);

    setContentView(R.layout.activity_new_ateneo);
        //Queste due inizializzazioni probabilmente non servono perchè vengono sovrascritte sotto senza essere usate prima
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
                String url1 = URLS.ATENEO_DETAIL_BASE_URL + article.getId();
                CustomTabsHelperFragment.open(AteneoActivity.this, mCustomTabsIntent, Uri.parse(url1), mCustomTabsFallback);
            }
        },R.drawable.guerrazzi);

        refreshList();

        mRecyclerView.setAdapter(mAdapter);




        ateneo=getResources().getStringArray(R.array.ateneo);

        Spinner spinnerA = (Spinner) navigationView.getMenu().findItem(R.id.navigation_drawer_ateneo).getActionView();
        spinnerA.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ateneo));
        spinnerA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AteneoActivity.this,"ateneo mannaggia dio vuoi funziona",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ingegneria= getResources().getStringArray(R.array.ingegneria);

        Spinner spinnerI = (Spinner) navigationView.getMenu().findItem(R.id.navigation_drawer_ingegneria).getActionView();
        spinnerI.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ingegneria));
        spinnerI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AteneoActivity.this,"ingegn mannaggia dio vuoi funziona",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        scienze=getResources().getStringArray(R.array.scienze);

        Spinner spinnerS = (Spinner) navigationView.getMenu().findItem(R.id.navigation_drawer_scienze_tecnologie).getActionView();
        spinnerS.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,scienze));
        spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AteneoActivity.this,"scienze mannaggia dio vuoi funziona",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        giurisprudenza= getResources().getStringArray(R.array.giur);

        Spinner spinnerG= (Spinner) navigationView.getMenu().findItem(R.id.navigation_drawer_giurisprudenza).getActionView();
        spinnerG.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,giurisprudenza));
        spinnerG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(AteneoActivity.this,"giur mannaggia dio vuoi funziona",Toast.LENGTH_SHORT).show();


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sea= getResources().getStringArray(R.array.economia);

        Spinner spinnerSe= (Spinner) navigationView.getMenu().findItem(R.id.navigation_drawer_sea).getActionView();
        spinnerSe.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sea));
        spinnerSe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(AteneoActivity.this,"sea mannaggia dio vuoi funziona",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }

    private void refreshList() {
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
