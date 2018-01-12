package com.nopeia.viewpager.myviewpage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.nopeia.viewpager.myviewpage.dummy.DummyContent;

public class Main2Activity extends AppCompatActivity implements HealthyTipFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HealthyTipFragment healthyTipFragment = (HealthyTipFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main2activity_fragment_container);

        if(healthyTipFragment == null){

            healthyTipFragment = new HealthyTipFragment();

            Log.d("AuthActivity", healthyTipFragment.toString());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    healthyTipFragment, R.id.main2activity_fragment_container);

        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Snackbar.make(findViewById(R.id.main2activity_coordinator_layout),
                "You clicked the item: " + item.id, Snackbar.LENGTH_SHORT).show();
    }

}
