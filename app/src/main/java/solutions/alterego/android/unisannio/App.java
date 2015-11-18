package solutions.alterego.android.unisannio;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;
import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import net.danlew.android.joda.JodaTimeAndroid;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import io.fabric.sdk.android.Fabric;
import solutions.alterego.android.unisannio.di.Component;

public class App extends MultiDexApplication {

    private Component component;

    public static IAndroidLogger l = NullAndroidLogger.instance;

    public static Component component(Context context) {
        return ((App) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        buildComponentAndInject();

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
            l = new DetailedAndroidLogger("UNISANNIO", IAndroidLogger.LoggingLevel.VERBOSE);
        }
        JodaTimeAndroid.init(this);
    }

    public void buildComponentAndInject() {
        component = Component.Initializer.init(this);
    }
}