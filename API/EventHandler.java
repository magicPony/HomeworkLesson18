package com.example.taras.homeworklesson18.API;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.taras.homeworklesson18.MainActivity;
import com.example.taras.homeworklesson18.R;

/**
 * Created by taras on 18.04.16.
 */

public final class EventHandler {

    private static Toast toast;

    public static boolean checkStatusNetworks() {
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) MainActivity
                .getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return mobileConnection.isConnected() || wifiConnection.isConnected();
    }

    public static void commitFragment(Fragment fragment, String fragmentTag, boolean addToBackStack) {
        if (addToBackStack) {
            MainActivity
                    .getInstance()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.rl_container_AM, fragment, fragmentTag)
                    .addToBackStack(fragmentTag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commitAllowingStateLoss();
        } else {
            MainActivity
                    .getInstance()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rl_container_AM, fragment, fragmentTag)
                    .commitAllowingStateLoss();
        }
    }

    public static void showToast(String message) {
        toast = Toast.makeText(MainActivity.getInstance(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
