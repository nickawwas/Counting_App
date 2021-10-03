package com.example.counting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.counting_app.database.AppDB;
import com.example.counting_app.database.entity.Event;

public class MainActivity extends AppCompatActivity {
    protected TextView totalCount;
    protected Button counterA, counterB, counterC, settings, showCounts;
    protected SharedPreferencesHelper sharedPreferencesHelper;
    protected AppDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferencesHelper = new SharedPreferencesHelper(MainActivity.this);

        // Connect to DB
        db = AppDB.getInstance(getApplicationContext());

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
                // Increment Total Count and Counter Count
                updateTotalCount("A");

                // Insert Event into DB
                db.eventDAO().insertEvent(new Event(0, sharedPreferencesHelper.getCounterName("A"), "1"));
            }
        });

        counterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increment Total Count and Counter Count
                updateTotalCount("B");

                // Insert Event into DB
                db.eventDAO().insertEvent(new Event(0, sharedPreferencesHelper.getCounterName("B"), "2"));
            }
        });

        counterC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increment Total Count and Counter Count
                updateTotalCount("C");

                // Insert Event into DB
                db.eventDAO().insertEvent(new Event(0, sharedPreferencesHelper.getCounterName("C"), "3"));
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

        //Go To Settings If Counters Names and Max Count are Not Defined
        if(!sharedPreferencesHelper.getAppInitStatus()) {
            goToSettingsActivity();
        // Set Button Text to Defined Counters or Max Count is Changed
        } else if(sharedPreferencesHelper.getChangedSettingsState()){
            setCounterButtonNames();

            sharedPreferencesHelper.resetTotalCount();
            initTotalCount();

            sharedPreferencesHelper.setChangedSettingsState(false);
        // Set Counter Names
        } else {
            setCounterButtonNames();
        }
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

    private void updateTotalCount(String counterId) {
        int maxCount = sharedPreferencesHelper.getMaxCount();
        int currentCount = sharedPreferencesHelper.getTotalCount();

        // Check if Count Can Be Increment (ie Max Count Not Yet ReacheD)
        if(currentCount < maxCount) {
            // Increment and Set Total Count Text
            sharedPreferencesHelper.incTotalCount();
            totalCount.setText(getTotalCountText());

            // Increment Button Count
            sharedPreferencesHelper.incCounterCount(counterId, sharedPreferencesHelper.getCounterCount(counterId));
        } else {
            Toast.makeText(getApplicationContext(), "Max Count (" + maxCount + ") Reached!", Toast.LENGTH_SHORT).show();
        }
    }

    // Set Buttons to New Counter Names
    private void setCounterButtonNames() {
        counterA.setText(sharedPreferencesHelper.getCounterName("A"));
        counterB.setText(sharedPreferencesHelper.getCounterName("B"));
        counterC.setText(sharedPreferencesHelper.getCounterName("C"));
    }
}