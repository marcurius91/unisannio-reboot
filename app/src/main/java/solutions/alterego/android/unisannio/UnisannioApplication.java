package solutions.alterego.android.unisannio;

import android.app.Application;
import android.content.Context;

import solutions.alterego.android.unisannio.di.D2EComponent;

public class UnisannioApplication extends Application {

    private D2EComponent component;

    public static D2EComponent component(Context context) {
        return ((UnisannioApplication) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        component = D2EComponent.Initializer.init(this);
    }
}