package com.example.taras.homeworklesson18;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taras.homeworklesson18.API.Constants;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by taras on 19.04.16.
 */
public final class HeadlessFragment extends Fragment{

    private ArrayList<Marker> markers;
    private static HeadlessFragment instance;

    public static HeadlessFragment getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        instance = this;
        markers = new ArrayList<>();

        if (savedInstanceState != null) {
            markers = (ArrayList<Marker>) savedInstanceState.getSerializable(Constants.MARKER_LIST);
        }

        setRetainInstance(true);
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.MARKER_LIST, markers);
    }

    public static void clear() {
        getInstance().markers.clear();
    }

    public static void addMarker(Marker marker) {
        instance.markers.add(marker);
    }

    public static int markersCount() {
        return instance.markers.size();
    }

    public static ArrayList<Marker> getMarkers() {
        return instance.markers;
    }

    public static Marker getMarker(int i) {
        return instance.markers.get(i);
    }
}
