package com.example.kawwas_ass1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kawwas_ass1.database.AppDB;

public class DataActivity extends AppCompatActivity {

    protected TextView eventA, eventB, eventC, totalEvents;
    protected ListView eventsList;
    protected SharedPreferencesHelper sharedPreferencesHelper;
    protected AppDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Add Buttons to Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferencesHelper = new SharedPreferencesHelper(DataActivity.this);

        // Connect to DB
        db = AppDB.getInstance(getApplicationContext());

        eventA = findViewById(R.id.eventLabelA);
        eventB = findViewById(R.id.eventLabelB);
        eventC = findViewById(R.id.eventLabelC);
        totalEvents = findViewById(R.id.totalEvents);

        // Scrollable List of All Counts
        eventsList = findViewById(R.id.eventDataList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Default Display Event Names, but When Toggled Display Counter 1, 2, 3 in List As Well
        initEventsCount(sharedPreferencesHelper.getDataMode());
    }

    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.data_menu, menu);
        return true;
    }

    // Select Option from Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggleEventNamesItem:
                // Toggle Between Event Name And Number
                initEventsCount(!sharedPreferencesHelper.getDataMode());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initEventsCount(boolean eventNameModeEnabled) {
        // Get Count for Each Event
        int eventACount = db.eventDAO().getEventsCountByNum("1");
        int eventBCount = db.eventDAO().getEventsCountByNum("2");
        int eventCCount = db.eventDAO().getEventsCountByNum("3");

        // Set Text Based on Data Mode Enabled
        if(eventNameModeEnabled) {
            eventA.setText(getDefinedEventCountText("A", eventACount));
            eventB.setText(getDefinedEventCountText("B", eventBCount));
            eventC.setText(getDefinedEventCountText("C", eventCCount));
        } else {
            eventA.setText(getNumberedEventCountText(R.string.eventLabelA, eventACount));
            eventB.setText(getNumberedEventCountText(R.string.eventLabelB, eventBCount));
            eventC.setText(getNumberedEventCountText(R.string.eventLabelC, eventCCount));
        }
        totalEvents.setText(getNumberedEventCountText(R.string.totalEvents, sharedPreferencesHelper.getTotalCount()));

        // Update Data Mode & List Based on Enabled Data Mode
        sharedPreferencesHelper.setDataMode(eventNameModeEnabled);
        getEventList(eventNameModeEnabled);
    }

    // Helper Functions to Provide Text Format for Counter Data
    private String getDefinedEventCountText(String counterId, int counterCount) {
        return sharedPreferencesHelper.getCounterName(counterId) + ": " + counterCount + " events";
    }

    private String getNumberedEventCountText(int stringResource, int counterCount) {
        return getResources().getString(stringResource) + " " + counterCount + " events";
    }

    private void getEventList(boolean eventNameModeEnabled) {
        eventsList.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, eventNameModeEnabled ? db.eventDAO().getEventsListByName() : db.eventDAO().getEventsListByNum()));
    }
}