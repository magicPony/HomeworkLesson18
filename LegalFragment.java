package com.example.taras.homeworklesson18;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by taras on 18.04.16.
 */
public final class LegalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.legal_fragment, container, false);

        String text;
        text = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(MainActivity.getInstance());
        ((TextView) view.findViewById(R.id.tv_LF)).setText(text);

        return view;
    }
}
