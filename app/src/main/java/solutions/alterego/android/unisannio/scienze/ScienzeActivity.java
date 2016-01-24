package solutions.alterego.android.unisannio.scienze;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.ButterKnife;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.MapsActivity;
import solutions.alterego.android.unisannio.NavigationDrawerActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.analytics.AnalyticsManager;
import solutions.alterego.android.unisannio.analytics.Screen;
import solutions.alterego.android.unisannio.map.UnisannioGeoData;

public class ScienzeActivity extends NavigationDrawerActivity {

    @Inject
    AnalyticsManager mAnalyticsManager;

    @BindColor(R.color.primaryColor)
    int mColorPrimary;

    protected Intent mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        App.component(this).inject(this);

        setContentView(R.layout.activity_scienze);
        ButterKnife.bind(this);

        mMap = new Intent(this, MapsActivity.class);

        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_web_page:
                mAnalyticsManager.track(new Screen(getString(R.string.scienze), getString(R.string.sito_web)));
                CustomTabsHelperFragment.open(this, mCustomTabsIntent, Uri.parse(URLS.SCIENZE), mCustomTabsFallback);
                break;
            case R.id.action_map:
                mAnalyticsManager.track(new Screen(getString(R.string.scienze), getString(R.string.mappa)));
                mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SCIENZE()));
                startActivity(mMap);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getNavigationDrawerMenuIdForThisActivity() {
        return R.id.avvisi_studenti_scienze_tecnologie;
    }

    @Override
    protected void onAppbarNavigationClick() {
        openNavigationDrawer();
    }
}
