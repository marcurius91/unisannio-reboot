package solutions.alterego.android.unisannio.ingegneria;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.MainActivity;
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
import butterknife.ButterKnife;
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
    private Intent mMap;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String personToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ingengeria_cercapersone);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        mMap = new Intent(this, MapsActivity.class);

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
                                ArrayList<Person> prsSrc = SearchPerson.searchPerson(personToSearch,persons);
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String fragmentName = getVisibleFragmentName(getVisibleFragment());
        Intent browserIntent;

        //Ateneo
        if(fragmentName.equalsIgnoreCase("AteneoAvvisiFragment")){
            switch (id){
                case R.id.action_web_page:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.ateneo), getString(R.string.sito_web)));
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.ATENEO));
                    startActivity(browserIntent);
                    break;
                case R.id.action_map:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.ateneo), getString(R.string.mappa)));
                    mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.ATENEO()));
                    startActivity(mMap);
                    break;
            }
        }

        //Ingegneria
        if(fragmentName.equalsIgnoreCase("IngegneriaAvvisiFragment")){
            switch (id){
                case R.id.action_web_page:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.ingegneria), getString(R.string.sito_web)));
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.INGEGNERIA));
                    startActivity(browserIntent);
                    break;
                case R.id.action_map:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.ingegneria), getString(R.string.mappa)));
                    mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.INGEGNERIA()));
                    startActivity(mMap);
                    break;
            }
        }

        //Scienze e Tecnologie
        if(fragmentName.equalsIgnoreCase("ScienzeAvvisiFragment")){
            switch (id){
                case R.id.action_web_page:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.scienze), getString(R.string.sito_web)));
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.SCIENZE));
                    startActivity(browserIntent);
                    break;
                case R.id.action_map:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.scienze), getString(R.string.mappa)));
                    mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SCIENZE()));
                    startActivity(mMap);
                    break;
            }
        }

        //Giurisprudenza
        if(fragmentName.equalsIgnoreCase("GiurisprudenzaAvvisiFragment")){
            switch (id){
                case R.id.action_web_page:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.giurisprudenza), getString(R.string.sito_web)));
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.GIURISPRUDENZA));
                    startActivity(browserIntent);
                    break;
                case R.id.action_map:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.giurisprudenza), getString(R.string.mappa)));
                    mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.GIURISPRUDENZA()));
                    startActivity(mMap);
                    break;
            }
        }

        //SEA
        if(fragmentName.equalsIgnoreCase("SeaAvvisiFragment")){
            switch (id){
                case R.id.action_web_page:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.sea), getString(R.string.sito_web)));
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.SEA));
                    startActivity(browserIntent);
                    break;
                case R.id.action_map:
                    //Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                    mAnalyticsManager.track(new Screen(getString(R.string.sea), getString(R.string.mappa)));
                    mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SEA()));
                    startActivity(mMap);
                    break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //get the active Fragment
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = IngengeriaCercapersoneActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.getUserVisibleHint())
                return (Fragment)fragment;
        }
        return null;
    }

    //get the name of the active Fragment
    public String getVisibleFragmentName(Fragment fragment){
        String str,fragmentName;
        str = fragment.toString();
        StringTokenizer st = new StringTokenizer(str,"{");
        fragmentName = st.nextToken();
        return fragmentName;
    }
}
