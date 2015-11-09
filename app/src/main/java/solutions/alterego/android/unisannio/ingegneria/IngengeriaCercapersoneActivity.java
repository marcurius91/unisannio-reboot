package solutions.alterego.android.unisannio.ingegneria;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.MapsActivity;
import solutions.alterego.android.unisannio.NavigationViewManager.NavigationViewManager;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.analytics.AnalyticsManager;
import solutions.alterego.android.unisannio.analytics.Screen;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiFragment;
import solutions.alterego.android.unisannio.cercapersone.CercapersoneAdapter;
import solutions.alterego.android.unisannio.cercapersone.Person;
import solutions.alterego.android.unisannio.cercapersone.SearchPerson;
import solutions.alterego.android.unisannio.map.UnisannioGeoData;


public class IngengeriaCercapersoneActivity extends AppCompatActivity{

    @Bind(R.id.cercapersone_ingegneria_recycle_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.cercapersone_ingegneria_swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @Inject
    AnalyticsManager mAnalyticsManager;

    private CercapersoneAdapter mAdapter;
    private ArrayList<Person> mPersons = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String personToSearch;
    NavigationViewManager navigationViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ingengeria_cercapersone);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        EditText tv_cercapersone = (EditText) findViewById(R.id.textView_ingegneria_cercapersone);
        Button btn_cercapersone = (Button) findViewById(R.id.button_ingegneria_cercapersone);

        mRecyclerView = (RecyclerView) findViewById(R.id.cercapersone_ingegneria_recycle_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.cercapersone_ingegneria_swipe_container);

        IngegneriaCercapersonePresenter icp = new IngegneriaCercapersonePresenter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CercapersoneAdapter(new ArrayList<>(),R.layout.ingegneria_cercapersone_list_person);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryColor));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        // Progress
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);

        mRecyclerView.setVisibility(View.VISIBLE);

        btn_cercapersone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                personToSearch = tv_cercapersone.getText().toString();

                icp.getPeople()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Person>>() {
                            @Override
                            public void onCompleted() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("ACTIVITY CERCAPERSONE onError()", e.toString());
                            }

                            @Override
                            public void onNext(ArrayList<Person> persons) {
                                ArrayList<Person> prsSrc = SearchPerson.searchPerson(personToSearch, persons);
                                mAdapter.addPersons(prsSrc);
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_cercapersone);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close) {


            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        //TODO Fix bug on next lines (We don't have a fragment to replace)
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, AteneoAvvisiFragment.newInstance(false)).commit();
        //navigationViewManager = new NavigationViewManager(drawerLayout,this);
        //navigationView = navigationViewManager.setUpNavigationDrawer(navigationView);

    }
}
