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
        //toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView = setUpNavigationDrawer(navigationView);

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

        switch (id){
            case R.id.action_web_page:
                Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                break;
            case R.id.action_map:
                Log.e("Active Fragment",getVisibleFragmentName(getVisibleFragment()));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public NavigationView setUpNavigationDrawer(NavigationView navigationView) {

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                Intent browserIntent;

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }

                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {

                    //Ateneo
                    case R.id.avvisi_ateneo:
                        fragmentManager.beginTransaction()
                            .replace(R.id.container, AteneoAvvisiFragment.newInstance(false))
                            .commit();
                        return true;
                    case R.id.avvisi_studenti:
                        mAnalyticsManager.track(new Screen(getString(R.string.ateneo), getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction().replace(R.id.container, AteneoAvvisiFragment.newInstance(true)).commit();
                        return true;
                    case R.id.sito_web:
                        mAnalyticsManager.track(new Screen(getString(R.string.ateneo), getString(R.string.sito_web)));
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.ATENEO));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_ateneo:
                        mAnalyticsManager.track(new Screen(getString(R.string.ateneo), getString(R.string.mappa)));
                        mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.ATENEO()));
                        startActivity(mMap);
                        return true;

                    //Ingegneria
                    case R.id.avvisi_dipartimento:
                        mAnalyticsManager.track(new Screen(getString(R.string.ingegneria), getString(R.string.avvisi_dipartimento)));
                        fragmentManager.beginTransaction()
                            .replace(R.id.container, IngegneriaAvvisiFragment.newInstance(true))
                            .commit();
                        return true;
                    case R.id.avvisi_studenti_ingegneria:
                        mAnalyticsManager.track(new Screen(getString(R.string.ingegneria), getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction()
                            .replace(R.id.container, IngegneriaAvvisiFragment.newInstance(false))
                            .commit();
                        return true;
                    case R.id.sito_web_ingegneria:
                        mAnalyticsManager.track(new Screen(getString(R.string.ingegneria), getString(R.string.sito_web)));
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.INGEGNERIA));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_ingegneria:
                        mAnalyticsManager.track(new Screen(getString(R.string.ingegneria), getString(R.string.mappa)));
                        mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.INGEGNERIA()));
                        startActivity(mMap);
                        return true;

                    // Scienze e tecnologie
                    case R.id.avvisi_studenti_scienze_tecnologie:
                        mAnalyticsManager.track(new Screen(getString(R.string.scienze), getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction()
                            .replace(R.id.container, new ScienzeAvvisiFragment())
                            .commit();
                        return true;
                    case R.id.sito_web_scienze_tecnologie:
                        mAnalyticsManager.track(new Screen(getString(R.string.scienze), getString(R.string.sito_web)));
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.SCIENZE));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_scienze_tecnologie:
                        mAnalyticsManager.track(new Screen(getString(R.string.scienze), getString(R.string.mappa)));
                        mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SCIENZE()));
                        startActivity(mMap);
                        return true;

                    // Giurisprudenza
                    case R.id.avvisi_studenti_giurisprudenza:
                        mAnalyticsManager.track(new Screen(getString(R.string.giurisprudenza), getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction()
                            .replace(R.id.container, GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_AVVISI))
                            .commit();
                        return true;
                    case R.id.comunicazioni:
                        mAnalyticsManager.track(new Screen(getString(R.string.giurisprudenza), getString(R.string.comunicazioni)));
                        fragmentManager.beginTransaction()
                            .replace(R.id.container, GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_COMUNICAZIONI))
                            .commit();
                        return true;
                    case R.id.sito_web_giurisprudenza:
                        mAnalyticsManager.track(new Screen(getString(R.string.giurisprudenza), getString(R.string.sito_web)));
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.GIURISPRUDENZA));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_giurisprudenza:
                        mAnalyticsManager.track(new Screen(getString(R.string.giurisprudenza), getString(R.string.mappa)));
                        mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.GIURISPRUDENZA()));
                        startActivity(mMap);
                        return true;

                    // SEA
                    case R.id.avvisi_studenti_sea:
                        mAnalyticsManager.track(new Screen(getString(R.string.sea), getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction()
                            .replace(R.id.container, SeaAvvisiFragment.newInstance(URLS.SEA_NEWS))
                            .commit();
                        return true;
                    case R.id.sito_web_sea:
                        mAnalyticsManager.track(new Screen(getString(R.string.sea), getString(R.string.sito_web)));
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.SEA));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_sea:
                        mAnalyticsManager.track(new Screen(getString(R.string.sea), getString(R.string.mappa)));
                        mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SEA()));
                        startActivity(mMap);
                        return true;

                    //About
                    case R.id.alteregosolution:
                        mAnalyticsManager.track(new Screen(getString(R.string.about), getString(R.string.sito_web)));
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.ALTEREGO));
                        startActivity(browserIntent);
                        return true;
                    case R.id.github:
                        mAnalyticsManager.track(new Screen(getString(R.string.about), getString(R.string.github)));
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.GITHUB));
                        startActivity(browserIntent);
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        return navigationView;
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