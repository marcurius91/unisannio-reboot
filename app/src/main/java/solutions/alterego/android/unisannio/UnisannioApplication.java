package solutions.alterego.android.unisannio;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;
import com.crashlytics.android.Crashlytics;

import android.app.Application;
import android.content.Context;

import io.fabric.sdk.android.Fabric;
import solutions.alterego.android.unisannio.di.Component;

public class UnisannioApplication extends Application {

    private Component component;

    public static IAndroidLogger l = NullAndroidLogger.instance;

    public static Component component(Context context) {
        return ((UnisannioApplication) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        buildComponentAndInject();

        if (BuildConfig.DEBUG) {
            l = new DetailedAndroidLogger("UNISANNIO", IAndroidLogger.LoggingLevel.VERBOSE);
        }
    }

    public void buildComponentAndInject() {
        component = Component.Initializer.init(this);
    }
}