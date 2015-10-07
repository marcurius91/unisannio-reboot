package solutions.alterego.android.unisannio.NavigationViewManager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import solutions.alterego.android.unisannio.MainActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.analytics.AnalyticsManager;
import solutions.alterego.android.unisannio.analytics.Screen;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiFragment;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaAvvisiFragment;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiFragment;
import solutions.alterego.android.unisannio.scienze.ScienzeAvvisiFragment;
import solutions.alterego.android.unisannio.sea.SeaAvvisiFragment;

public class NavigationViewManager {

    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;
    public Context context;


    @Inject
    AnalyticsManager mAnalyticsManager;

    public NavigationViewManager(NavigationView navigationView, FragmentManager fragmentManager,DrawerLayout drawerLayout,Context context){
        this.navigationView = navigationView;
        this.fragmentManager = fragmentManager;
        this.drawerLayout = drawerLayout;
        this.context = context;


    }
    public NavigationView setUpNavigationDrawer() {

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
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
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.ateneo), context.getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction().replace(R.id.container, AteneoAvvisiFragment.newInstance(true)).commit();
                        return true;

                    //Ingegneria
                    case R.id.avvisi_dipartimento:
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.ingegneria), context.getString(R.string.avvisi_dipartimento)));
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, IngegneriaAvvisiFragment.newInstance(true))
                                .commit();
                        return true;
                    case R.id.avvisi_studenti_ingegneria:
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.ingegneria), context.getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, IngegneriaAvvisiFragment.newInstance(false))
                                .commit();
                        return true;

                    // Scienze e tecnologie
                    case R.id.avvisi_studenti_scienze_tecnologie:
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.scienze), context.getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, new ScienzeAvvisiFragment())
                                .commit();
                        return true;

                    // Giurisprudenza
                    case R.id.avvisi_studenti_giurisprudenza:
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.giurisprudenza), context.getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_AVVISI))
                                .commit();
                        return true;
                    case R.id.comunicazioni:
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.giurisprudenza), context.getString(R.string.comunicazioni)));
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, GiurisprudenzaAvvisiFragment.newInstance(URLS.GIURISPRUDENZA_COMUNICAZIONI))
                                .commit();
                        return true;

                    // SEA
                    case R.id.avvisi_studenti_sea:
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.sea), context.getString(R.string.avvisi_studenti)));
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, SeaAvvisiFragment.newInstance(URLS.SEA_NEWS))
                                .commit();
                        return true;

                    //About
                    case R.id.alteregosolution:
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.about), context.getString(R.string.sito_web)));
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.ALTEREGO));
                        context.startActivity(browserIntent);
                        return true;
                    case R.id.github:
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.about), context.getString(R.string.github)));
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLS.GITHUB));
                        context.startActivity(browserIntent);
                        return true;

                    default:
                        Toast.makeText(context.getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        return navigationView;
    }

}
