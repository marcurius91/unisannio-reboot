package solutions.alterego.android.unisannio;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    private Intent map;

    @Bind(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        map = new Intent(this, MapsActivity.class);

        UnisannioApplication.component(this).inject(this);
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
        getFragmentManager().beginTransaction().replace(R.id.container, AteneoAvvisiFragment.newInstance(false)).commit();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,mToolbar,R.string.drawer_open, R.string.drawer_close){


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
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public NavigationView setUpNavigationDrawer(NavigationView navigationView){

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                FragmentManager fragmentManager = getFragmentManager();
                Intent browserIntent;

                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    //Ateneo
                    case R.id.avvisi_ateneo:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, AteneoAvvisiFragment.newInstance(false))
                                .commit();
                        return true;
                    case R.id.avvisi_studenti:
                        fragmentManager.beginTransaction().replace(R.id.container,AteneoAvvisiFragment.newInstance(true)).commit();
                        return true;
                    case R.id.sito_web:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.ATENEO));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_ateneo:
                        map.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.ATENEO()));
                        startActivity(map);
                        return true;

                    //Ingegneria
                    case R.id.avvisi_dipartimento:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, IngegneriaAvvisiFragment.newInstance(false))
                                .commit();
                        return true;
                    case R.id.avvisi_studenti_ingegneria:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, IngegneriaAvvisiFragment.newInstance(true))
                                .commit();
                        return true;
                    case R.id.sito_web_ingegneria:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.INGEGNERIA));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_ingegneria:
                        map.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.INGEGNERIA()));
                        startActivity(map);
                        return true;

                    // Scienze e tecnologie
                    case R.id.avvisi_studenti_scienze_tecnologie:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, new ScienzeAvvisiFragment())
                                .commit();
                        return true;
                    case R.id.sito_web_scienze_tecnologie:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.SCIENZE));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_scienze_tecnologie:
                        map.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SCIENZE()));
                        startActivity(map);
                        return true;

                    // Giurisprudenza
                    case R.id.avvisi_studenti_giurisprudenza:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_AVVISI))
                                .commit();
                        return true;
                    case R.id.comunicazioni:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_COMUNICAZIONI))
                                .commit();
                        return true;
                    case R.id.sito_web_giurisprudenza:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.GIURISPRUDENZA));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_giurisprudenza:
                        map.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.GIURISPRUDENZA()));
                        startActivity(map);
                        return true;

                    // SEA
                    case R.id.avvisi_studenti_sea:
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, SeaAvvisiFragment.newInstance(URLS.SEA_NEWS))
                                .commit();
                        return true;
                    case R.id.sito_web_sea:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.SEA));
                        startActivity(browserIntent);
                        return true;
                    case R.id.mappa_sea:
                        map.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SEA()));
                        startActivity(map);
                        return true;

                    //About
                    case R.id.alteregosolution:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.ALTEREGO));
                        startActivity(browserIntent);
                        return true;
                    case R.id.github:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.GITHUB));
                        startActivity(browserIntent);
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });
        return navigationView;
    }
}