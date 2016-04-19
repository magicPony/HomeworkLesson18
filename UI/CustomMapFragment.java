package com.example.taras.homeworklesson18.UI;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taras.homeworklesson18.API.EventHandler;
import com.example.taras.homeworklesson18.HeadlessFragment;
import com.example.taras.homeworklesson18.MainActivity;
import com.example.taras.homeworklesson18.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by taras on 19.04.16.
 */
public final class CustomMapFragment extends Fragment implements GoogleMap.OnMapClickListener, OnMapReadyCallback {

    private static CustomMapFragment instance = null;
    private GoogleMap map;
    private LocationManager locationManager;
    private Circle circle;
    private Polyline polyline;

    public static CustomMapFragment getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        instance = this;
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        MapView mapView = (MapView) view.findViewById(R.id.f_map_MF);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return view;
    }

    public static void cleanMap() {
        CustomMapFragment fragment = getInstance();
        fragment.map.clear();
        HeadlessFragment.clear();
        instance.redraw();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        HeadlessFragment.addMarker(createMarker(latLng));
        redraw();
    }

    private Marker createMarker(LatLng latLng) {
        Random random = new Random(30);

        float color = (float) random.nextInt(360);
        String lat = String.format("Latitude  = %.4f", latLng.latitude);
        String lng = String.format("Longitude = %.4f", latLng.longitude);
        return map.addMarker(new MarkerOptions().title(lat + "/" + lng)
                .icon(BitmapDescriptorFactory.defaultMarker(color))
                .draggable(false)
                .snippet("Marker #" + Integer.toString(HeadlessFragment.markersCount() + 1))
                .position(latLng));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        locationManager = (LocationManager) MainActivity
                .getInstance()
                .getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            EventHandler.showToast(getString(R.string.gps_disabled));
            MainActivity.getInstance().finish();
        }

        if (ActivityCompat.checkSelfPermission(MainActivity.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            EventHandler.showToast(getString(R.string.not_enough_permisson));
            MainActivity.getInstance().finish();
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

        //map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(this);

        for (Marker marker : HeadlessFragment.getMarkers()) {
            createMarker(marker.getPosition());
        }

        redraw();
    }

    public void redraw() {
        Log.v("asasd", Integer.toString(HeadlessFragment.markersCount()));

        if (polyline != null) {
            polyline.remove();
            polyline = null;
        }

        if (circle != null) {
            circle.remove();
            circle = null;
        }

        int markersCount = HeadlessFragment.markersCount();

        if (markersCount == 0) {
            return;
        }

        if (markersCount == 1) {
            drawCircle(HeadlessFragment.getMarker(0));
            return;
        }

        ArrayList<LatLng> vertices = new ArrayList<>();

        for (Marker marker : HeadlessFragment.getMarkers()) {
            vertices.add(marker.getPosition());
        }

        if (markersCount == 3 || markersCount == 4) {
            vertices.add(vertices.get(0));
        }

        drawPolyline(vertices);
    }

    private void drawPolyline(ArrayList<LatLng> vertices) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.CYAN);

        for (LatLng vertex : vertices) {
            polylineOptions.add(vertex);
        }

        polyline = map.addPolyline(polylineOptions);
    }

    private void drawCircle(Marker marker) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(marker.getPosition());
        circleOptions.radius(200);
        circleOptions.fillColor(Color.argb(100, 0, 0, 100));
    }
}
