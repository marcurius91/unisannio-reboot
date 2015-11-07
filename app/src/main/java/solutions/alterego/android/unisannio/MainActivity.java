package solutions.alterego.android.unisannio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import solutions.alterego.android.unisannio.NavigationViewManager.NavigationViewManager;
import solutions.alterego.android.unisannio.analytics.AnalyticsManager;
import solutions.alterego.android.unisannio.analytics.Screen;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiFragment;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaAvvisiFragment;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiFragment;
import solutions.alterego.android.unisannio.map.UnisannioGeoData;
import solutions.alterego.android.unisannio.scienze.ScienzeAvvisiFragment;
import solutions.alterego.android.unisannio.sea.SeaAvvisiFragment;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    //private Toolbar toolbar;
    private NavigationView navigationView;

    private DrawerLayout drawerLayout;

    NavigationViewManager navigationViewManager;
    private Intent mMap;

    @Bind(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @Inject
    AnalyticsManager mAnalyticsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.component(this).inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMap = new Intent(this, MapsActivity.class);
        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //set fragment Avvisi ateneo default when the app start
        getSupportFragmentManager().beginTransaction().replace(R.id.container, AteneoAvvisiFragment.newInstance(false)).commit();
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

        navigationViewManager = new NavigationViewManager(drawerLayout,this);
        navigationView = navigationViewManager.setUpNavigationDrawer(navigationView);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
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