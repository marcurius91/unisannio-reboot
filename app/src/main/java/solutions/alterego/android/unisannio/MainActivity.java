package solutions.alterego.android.unisannio;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import butterknife.ButterKnife;
import butterknife.InjectView;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiFragment;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaAvvisiFragment;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiFragment;
import solutions.alterego.android.unisannio.map.MapFragment;
import solutions.alterego.android.unisannio.map.UnisannioGeoData;
import solutions.alterego.android.unisannio.navigation_drawer.NavigationDrawerCallbacks;
import solutions.alterego.android.unisannio.navigation_drawer.NavigationDrawerFragment;
import solutions.alterego.android.unisannio.scienze.ScienzeAvvisiFragment;
import solutions.alterego.android.unisannio.sea.SeaAvvisiFragment;


public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

    @InjectView(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @InjectView(R.id.drawer)
    DrawerLayout mDrawerLayout;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        UnisannioApplication.component(this).inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, mDrawerLayout, mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        Intent browserIntent;

        switch (position) {
            // ATENEO
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AteneoAvvisiFragment.newInstance(false))
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AteneoAvvisiFragment.newInstance(true))
                        .commit();
                break;
            case 3:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.ATENEO));
                startActivity(browserIntent);
                break;
            case 4:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance(UnisannioGeoData.ATENEO()))
                        .commit();
                break;

            // INGEGNERIA
            case 6:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, IngegneriaAvvisiFragment.newInstance(false))
                        .commit();
                break;
            case 7:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, IngegneriaAvvisiFragment.newInstance(true))
                        .commit();
                break;
            case 8:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.INGEGNERIA));
                startActivity(browserIntent);
                break;
            case 9:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance(UnisannioGeoData.INGEGNERIA()))
                        .commit();
                break;

            // SCIENZE
            case 11:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ScienzeAvvisiFragment())
                        .commit();
                break;
            case 12:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.SCIENZE));
                startActivity(browserIntent);
                break;
            case 13:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance(UnisannioGeoData.SCIENZE()))
                        .commit();
                break;

            // GIURISPRUDENZA
            case 15:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_AVVISI))
                        .commit();
                break;
            case 16:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_COMUNICAZIONI))
                        .commit();
                break;
            case 17:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.GIURISPRUDENZA));
                startActivity(browserIntent);
                break;
            case 18:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance(UnisannioGeoData.GIURISPRUDENZA()))
                        .commit();
                break;

            // SEA
            case 20:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SeaAvvisiFragment.newInstance(URLS.SEA_NEWS))
                        .commit();
                break;
            case 21:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.SEA));
                startActivity(browserIntent);
                break;
            case 22:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance(UnisannioGeoData.SEA()))
                        .commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
