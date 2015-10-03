package solutions.alterego.android.unisannio.analytics;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONObject;

import android.content.Context;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;
import javax.inject.Singleton;

import solutions.alterego.android.unisannio.BuildConfig;

@Singleton
public class AnalyticsManager {

    @StringDef({INGEGNERIA, SEA, GIURISPRUDENZA, SCIENZA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Faculties {

    }

    public static final String INGEGNERIA = "Ingegneria";

    public static final String SEA = "S.E.A.";

    public static final String GIURISPRUDENZA = "Giurisprudenza";

    public static final String SCIENZA = "Scienza";

    private final MixpanelAPI mMixpanel;

    @Inject
    public AnalyticsManager(Context context) {
        mMixpanel = MixpanelAPI.getInstance(context, BuildConfig.MIXPANEL_TOKEN);
    }

    public void track(@Faculties String faculty) {
        JSONObject props = new JSONObject();
        mMixpanel.track(faculty, props);
    }
}
