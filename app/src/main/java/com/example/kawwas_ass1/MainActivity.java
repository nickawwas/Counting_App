package com.example.kawwas_ass1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    protected TextView totalCount;
    protected Button counterA, counterB, counterC, settings, showCounts;
    protected SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferencesHelper = new SharedPreferencesHelper(MainActivity.this);

        // Create Object & Initialize Total Count
        totalCount = findViewById(R.id.totalCount);
        initTotalCount();

        // Create Object & Listener for Settings
        settings = findViewById(R.id.mainSettingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettingsActivity();
            }
        });

        // Create Objects for Counters
        counterA = findViewById(R.id.mainCounterButtonA);
        counterB = findViewById(R.id.mainCounterButtonB);
        counterC = findViewById(R.id.mainCounterButtonC);

        counterA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTotalCount();
            }
        });

        counterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTotalCount();
            }
        });

        counterC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTotalCount();
            }
        });

        // Create Object & Listener for Data
        showCounts = findViewById(R.id.showCountsButton);
        showCounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDataActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Go To Settings If Counters Names are Null
    }

    // Navigation to Settings and Data Activities
    private void goToSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void goToDataActivity() {
        Intent intent = new Intent(this, DataActivity.class);
        startActivity(intent);
    }

    // Get, Initialize, and Update Total Count Text
    private String getTotalCountText() {
        return getResources().getString(R.string.totalCount) + " " + sharedPreferencesHelper.getTotalCount();
    }

    private void initTotalCount() {
        totalCount.setText(getTotalCountText());
    }

    private void updateTotalCount() {
        sharedPreferencesHelper.incTotalCount();
        totalCount.setText(getTotalCountText());
    }

/*
Toast.makeText(getApplicationContext(), "Toast Message Here", Toast.LENGTH_LONG).show();
 */
}