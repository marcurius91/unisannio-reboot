package solutions.alterego.android.unisannio.navigation;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import javax.inject.Inject;

import butterknife.BindColor;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import solutions.alterego.android.unisannio.MainActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.analytics.AnalyticsManager;
import solutions.alterego.android.unisannio.analytics.Screen;
import solutions.alterego.android.unisannio.ateneo.AteneoAvvisiFragment;
import solutions.alterego.android.unisannio.giurisprudenza.GiurisprudenzaAvvisiFragment;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaAvvisiFragment;
import solutions.alterego.android.unisannio.ingegneria.IngengeriaCercapersoneActivity;
import solutions.alterego.android.unisannio.scienze.ScienzeAvvisiFragment;
import solutions.alterego.android.unisannio.sea.SeaAvvisiFragment;

public class NavigationViewManager extends FragmentActivity {

    private DrawerLayout drawerLayout;
    public Context context;
    private Intent cercapersoneIngegneria;


    @Inject
    AnalyticsManager mAnalyticsManager;

    @BindColor(R.color.primaryColor)
    int mColorPrimary;

    private CustomTabsHelperFragment mCustomTabsHelperFragment;

    private CustomTabsIntent mCustomTabsIntent;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public NavigationViewManager(DrawerLayout drawerLayout, Context context) {
        this.drawerLayout = drawerLayout;
        this.context = context;


    }

    public NavigationView setUpNavigationDrawer(NavigationView navigationView) {

        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo((FragmentActivity) context);
        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
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
                    case R.id.cercapersone_ingegneria:
                        cercapersoneIngegneria = new Intent(context, IngengeriaCercapersoneActivity.class);
                        context.startActivity(cercapersoneIngegneria);
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
                        CustomTabsHelperFragment.open(((FragmentActivity) context),mCustomTabsIntent, Uri.parse(URLS.ALTEREGO), mCustomTabsFallback);
                        return true;
                    case R.id.github:
                        //mAnalyticsManager.track(new Screen(context.getString(R.string.about), context.getString(R.string.github)));
                        CustomTabsHelperFragment.open(((FragmentActivity) context),mCustomTabsIntent, Uri.parse(URLS.GITHUB), mCustomTabsFallback);
                        return true;

                    default:
                        Toast.makeText(context.getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        return navigationView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NavigationViewManager Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://solutions.alterego.android.unisannio.navigation/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "NavigationViewManager Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://solutions.alterego.android.unisannio.navigation/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
