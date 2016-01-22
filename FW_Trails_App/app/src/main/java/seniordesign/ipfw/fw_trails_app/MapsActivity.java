package seniordesign.ipfw.fw_trails_app;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.maps.android.kml.KmlLayer;

import java.io.InputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    /**
     * Below are the 6 parts of the Android activity lifecycle in the following order:
     * onCreate
     * onStart
     * onResume
     * onPause
     * onStop
     * onDestroy
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * The method below is called when the Google map is ready
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMapToolbarEnabled(false);

        InputStream is = getResources().openRawResource(R.raw.doc);
//        KmlLayer layer = new KmlLayer(getMap(), R.raw.kmlFile, getApplicationContext());
//        try {
//            KmlLayer layer = new KmlLayer(mMap, is, getApplicationContext());
//            layer.addLayerToMap();
//        }
//        catch(org.xmlpull.v1.XmlPullParserException e){
//
//        }
//        catch(java.io.IOException e){
//
//        }
    }
}
