package com.example.taras.homeworklesson18;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public final class MainActivity extends AppCompatActivity {

    private GoogleMap map;
    private static MainActivity instance;
    private List<Marker> markers;
    private LocationManager locationManager;
    private LatLng currentLocation;

    public static MainActivity getInstance() {
        return instance;
    }

    // googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        if (!EventHandler.checkStatusNetworks()) {
            EventHandler.showToast(getString(R.string.no_internet_connection));
            finish();
        }

        ((MapFragment) getFragmentManager().findFragmentById(R.id.f_map_AM)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                initMap();
            }
        });
    }

    private void initMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            EventHandler.showToast(getString(R.string.not_enough_permisson));
            finish();
        }

        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        //map.getUiSettings().setIndoorLevelPickerEnabled(true);
        //map.getUiSettings().setRotateGesturesEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(true);
        //map.getUiSettings().setTiltGesturesEnabled(true);
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //map.getUiSettings().setZoomGesturesEnabled(true);
        Location location = map.getMyLocation();
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        //moveCamera(currentLocation);

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    private void moveCamera(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        map.moveCamera(cameraUpdate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_clean:
                cleanMap();
                break;

            case R.id.mi_legal:
                EventHandler.commitFragment(new LegalFragment(), Constants.LEGAL_FRAGMENT);
                break;
        }

        return true;
    }

    private void cleanMap() {
        map.clear();
        markers.clear();
    }
}
