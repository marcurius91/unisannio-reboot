package solutions.alterego.android.unisannio;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;
import org.chromium.customtabsclient.CustomTabsActivityHelper;
import solutions.alterego.android.unisannio.navigation.Navigator;

public abstract class BaseActivity extends AppCompatActivity {

    private Navigator navigator;

    private Toolbar appbar;

    private CustomTabsHelperFragment mCustomTabsHelperFragment;

    protected CustomTabsIntent mCustomTabsIntent;

    protected Intent mMap;

    @Override @CallSuper protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = new Navigator(this);
    }

    @Override public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        findAndSetAppbar();
    }

    @Override public void setContentView(View view) {
        super.setContentView(view);
        findAndSetAppbar();

        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);
        int color = getResources().getColor(R.color.primaryColor);
        mCustomTabsIntent = new CustomTabsIntent.Builder().enableUrlBarHiding().setToolbarColor(color).setShowTitle(true).build();

        mMap = new Intent(this, MapsActivity.class);
    }

    @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        findAndSetAppbar();
    }

    protected final void findAndSetAppbar() {
        appbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if (appbar != null) {
            setSupportActionBar(appbar);
            appbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    BaseActivity.this.onAppbarNavigationClick();
                }
            });
        }
    }

    protected void onAppbarNavigationClick() {
        navigate().upToParent();
    }

    @NonNull protected final Navigator navigate() {
        return navigator;
    }

    @Nullable protected Toolbar getSupportAppBar() {
        return appbar;
    }

    protected final CustomTabsActivityHelper.CustomTabsFallback mCustomTabsFallback = new CustomTabsActivityHelper.CustomTabsFallback() {
        @Override public void openUri(Activity activity, Uri uri) {
            Toast.makeText(activity, R.string.custom_tab_error, Toast.LENGTH_SHORT).show();
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(activity, R.string.custom_tab_error_activity, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
