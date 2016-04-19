package com.example.taras.homeworklesson18;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.taras.homeworklesson18.API.Constants;
import com.example.taras.homeworklesson18.API.EventHandler;
import com.example.taras.homeworklesson18.UI.CustomMapFragment;
import com.example.taras.homeworklesson18.UI.LegalFragment;

public final class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!EventHandler.checkStatusNetworks()) {
            EventHandler.showToast(getString(R.string.no_internet_connection));
            finish();
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(new HeadlessFragment(), Constants.HEADLESS_FRAGMENT)
                    .commitAllowingStateLoss();

            EventHandler.commitFragment(new CustomMapFragment(), Constants.MAP_FRAGMENT, false);
        } else {
            CustomMapFragment.getInstance().redraw();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_clean :
                CustomMapFragment.cleanMap();
                break;

            case R.id.mi_legal :
                EventHandler.commitFragment(new LegalFragment(), Constants.LEGAL_FRAGMENT, true);
                break;
        }

        return true;
    }
}
