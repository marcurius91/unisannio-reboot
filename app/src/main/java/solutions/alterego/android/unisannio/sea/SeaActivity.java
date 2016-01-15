package solutions.alterego.android.unisannio.sea;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.ButterKnife;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import solutions.alterego.android.unisannio.App;
import solutions.alterego.android.unisannio.NavigationDrawerActivity;
import solutions.alterego.android.unisannio.R;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.analytics.AnalyticsManager;
import solutions.alterego.android.unisannio.analytics.Screen;
import solutions.alterego.android.unisannio.map.UnisannioGeoData;

public class SeaActivity extends NavigationDrawerActivity {

    @Inject
    AnalyticsManager mAnalyticsManager;

    @BindColor(R.color.primaryColor)
    int mColorPrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.component(this).inject(this);

        setContentView(R.layout.activity_sea);
        ButterKnife.bind(this);

        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();
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
                mAnalyticsManager.track(new Screen(getString(R.string.sea), getString(R.string.sito_web)));
                CustomTabsHelperFragment.open(this, mCustomTabsIntent, Uri.parse(URLS.SEA), mCustomTabsFallback);
                break;
            case R.id.action_map:
                mAnalyticsManager.track(new Screen(getString(R.string.sea), getString(R.string.mappa)));
                mMap.putParcelableArrayListExtra("MARKERS", ((ArrayList) UnisannioGeoData.SEA()));
                startActivity(mMap);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getNavigationDrawerMenuIdForThisActivity() {
        return R.id.drawer_section_sea;
    }

    @Override
    protected void onAppbarNavigationClick () {
        openNavigationDrawer();
    }
}
