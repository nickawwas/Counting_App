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
        initEventsCount(true);
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
        int eventACount = sharedPreferencesHelper.getCounterCount("A");
        int eventBCount = sharedPreferencesHelper.getCounterCount("B");
        int eventCCount = sharedPreferencesHelper.getCounterCount("C");

        // Set Text Based on Data Mode Enabled
        if (eventNameModeEnabled) {
            eventA.setText(getFullEventText(getEventName("A"), eventACount));
            eventB.setText(getFullEventText(getEventName("B"), eventBCount));
            eventC.setText(getFullEventText(getEventName("C"), eventCCount));
        } else {
            eventA.setText(getFullEventText(getEventNameByResource(R.string.eventLabelA),  eventACount));
            eventB.setText(getFullEventText(getEventNameByResource(R.string.eventLabelB), eventBCount));
            eventC.setText(getFullEventText(getEventNameByResource(R.string.eventLabelC), eventCCount));
        }
        totalEvents.setText(getEventNameByResource(R.string.totalEvents) + sharedPreferencesHelper.getTotalCount());

        // Update Data Mode & List Based on Enabled Data Mode
        sharedPreferencesHelper.setDataMode(eventNameModeEnabled);
        getEventList(eventNameModeEnabled);
    }

    // Get Event Name from SharedPreferences W/ Text Formatted for TextEdit
    private String getEventName(String counterId) {
        return sharedPreferencesHelper.getCounterName(counterId) + ": ";
    }

    // Get Event Name from Resource W/ Text Formatted for TextEdit
    private String getEventNameByResource(int resourceId) {
        return getResources().getString(resourceId) + " ";
    }

    // Get Full Event Text (Name, Count & Format) for TextEdit
    private String getFullEventText(String eventName, int eventCount) {
        return eventName + eventCount + " events";
    }

    private void getEventList(boolean eventNameModeEnabled) {
        eventsList.setAdapter(new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, eventNameModeEnabled ? db.eventDAO().getEventsListByName() : db.eventDAO().getEventsListByNum()));
    }
}