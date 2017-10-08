package solutions.alterego.android.unisannio

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import solutions.alterego.android.unisannio.map.UniPoint
import java.util.ArrayList

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var markers = ArrayList<UniPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("MARKERS", markers)
        super.onSaveInstanceState(outState)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        markers = intent.getParcelableArrayListExtra("MARKERS")

        markers.forEach { marker ->
            googleMap.addMarker(
                MarkerOptions()
                    .position(marker.geopoint)
                    .title(marker.name)
                    .snippet(marker.address))
        }

        // For zooming automatically to the last Dropped PIN Location
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markers[markers.size - 1].geopoint, 16.0f))
    }
}
