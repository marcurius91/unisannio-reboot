package solutions.alterego.android.unisannio;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import solutions.alterego.android.unisannio.map.UniPoint;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;

    private ArrayList<UniPoint> mMarkers = new ArrayList<>();

    private void setUpMap(List<UniPoint> markers) {
        // For showing a move to my location button
        mMap.setMyLocationEnabled(true);

        for (UniPoint uniPoint : markers) {
            mMap.addMarker(new MarkerOptions().position(uniPoint.getGeopoint()).title(uniPoint.getName()).snippet(uniPoint.getAddress()));
        }

        // For zooming automatically to the last Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markers.get(markers.size() - 1).getGeopoint(), 16.0f));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMarkers = getIntent().getParcelableArrayListExtra("MARKERS");

        setUpMapIfNeeded(mMarkers);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("MARKERS", mMarkers);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded(mMarkers);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap(List<UniPoint>)} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded(final List<UniPoint> markers) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            // Check if we were successful in obtaining the map.

            getMap().subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<GoogleMap, Boolean>() {
                        @Override public Boolean call(GoogleMap googleMap) {
                            return googleMap != null;
                        }
                    })
                    .subscribe(new Action1<GoogleMap>() {
                        @Override public void call(GoogleMap googleMap) {
                            mMap = googleMap;
                            MapsActivity.this.setUpMap(markers);
                        }
                    });
        }
    }

    private Observable<GoogleMap> getMap() {
        return Observable.create(new Observable.OnSubscribe<GoogleMap>() {
            @Override public void call(Subscriber<? super GoogleMap> subscriber) {
                GoogleMap map = ((SupportMapFragment) MapsActivity.this.getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

                subscriber.onNext(map);
                subscriber.onCompleted();
            }
        });
    }
}
