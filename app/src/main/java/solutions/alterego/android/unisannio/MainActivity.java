package solutions.alterego.android.unisannio;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import butterknife.ButterKnife;
import butterknife.InjectView;
import solutions.alterego.android.unisannio.ateneo.AteneoFragment;
import solutions.alterego.android.unisannio.ingegneria.IngegneriaFragment;
import solutions.alterego.android.unisannio.navigation_drawer.NavigationDrawerCallbacks;
import solutions.alterego.android.unisannio.navigation_drawer.NavigationDrawerFragment;
import solutions.alterego.android.unisannio.scienze.ScienzeFragment;
import solutions.alterego.android.unisannio.utils.CollectionUtils;


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
        switch (position) {
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new AteneoFragment())
                        .commit();
                break;
            case 5:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new IngegneriaFragment())
                        .commit();
                break;
            case 6:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ScienzeFragment())
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
