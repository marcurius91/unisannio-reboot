package solutions.alterego.android.unisannio.analytics;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import solutions.alterego.android.unisannio.BuildConfig;
import solutions.alterego.android.unisannio.UnisannioApplication;

@Singleton
public class AnalyticsManager {

    private final MixpanelAPI mMixpanel;

    @Inject
    public AnalyticsManager(Context context) {
        mMixpanel = MixpanelAPI.getInstance(context, BuildConfig.MIXPANEL_TOKEN);
    }

    public void track(Screen screen) {
        JSONObject props = new JSONObject();
        try {
            props.put("Section", screen.getSection());
            props.put("Screen", screen.getScreen());
        } catch (JSONException e) {
            UnisannioApplication.l.e(e);
        }
        mMixpanel.track(screen.getSection(), props);
    }
}
