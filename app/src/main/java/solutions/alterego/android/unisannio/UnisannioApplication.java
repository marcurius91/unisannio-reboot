package solutions.alterego.android.unisannio;

import android.app.Application;
import android.content.Context;

import solutions.alterego.android.unisannio.di.Component;

public class UnisannioApplication extends Application {

    private Component component;

    public static Component component(Context context) {
        return ((UnisannioApplication) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        component = Component.Initializer.init(this);
    }
}