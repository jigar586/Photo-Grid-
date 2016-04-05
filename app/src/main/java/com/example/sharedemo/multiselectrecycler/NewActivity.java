package com.example.sharedemo.multiselectrecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Avni on 23-03-2016.
 */
public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.next);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        // ab.setLogo(R.drawable.ic_done_all_black_18dp);
        ab.setDisplayUseLogoEnabled(true);
        ab.setHomeButtonEnabled(true);

    }
}
