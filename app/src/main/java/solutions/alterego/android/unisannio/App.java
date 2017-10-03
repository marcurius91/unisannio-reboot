package solutions.alterego.android.unisannio;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import net.danlew.android.joda.JodaTimeAndroid;
import solutions.alterego.android.unisannio.di.Component;
import timber.log.Timber;

public class App extends MultiDexApplication {

    private Component component;

    public static Component component(Context context) {
        return ((App) context.getApplicationContext()).component;
    }

    @Override public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        buildComponentAndInject();

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
        JodaTimeAndroid.init(this);

        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    public void buildComponentAndInject() {
        component = Component.Initializer.init(this);
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
            if (t != null) {
                if (priority == Log.ERROR) {
                    Crashlytics.log(priority, tag, message);
                    Crashlytics.logException(t);
                }
            }
        }
    }
}
