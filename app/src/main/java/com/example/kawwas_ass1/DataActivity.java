package com.example.kawwas_ass1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kawwas_ass1.database.AppDB;
import com.example.kawwas_ass1.database.entity.Event;

public class DataActivity extends AppCompatActivity {

    protected TextView eventA, eventB, eventC, totalEvents;
    protected ListView eventsList;
    protected SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Add Buttons to Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferencesHelper = new SharedPreferencesHelper(DataActivity.this);

        eventA = findViewById(R.id.eventLabelA);
        eventB = findViewById(R.id.eventLabelB);
        eventC = findViewById(R.id.eventLabelC);
        totalEvents = findViewById(R.id.totalEvents);

        // Scrollable List of All Counts (Stack)
        eventsList = findViewById(R.id.eventDataList);


    }

    @Override
    protected void onStart() {
        super.onStart();

        //Default Display Event Names, but When Toggled Display Counter 1, 2, 3 in List As Well
        sharedPreferencesHelper.setDataMode(true);
        initEventsCount();

        // Connect DB
        AppDB db = AppDB.getInstance(getApplicationContext());
        db.eventDAO().insertEvents(new Event(0, "Test Event"));
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
                sharedPreferencesHelper.setDataMode(!sharedPreferencesHelper.getDataMode());
                initEventsCount();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initEventsCount() {
        String eventACount = "1";
        String eventBCount = "2";
        String eventCCount = "Nice";

        if(sharedPreferencesHelper.getDataMode()) {
            eventA.setText(getDefinedEventCountText("A", eventACount));
            eventB.setText(getDefinedEventCountText("B", eventBCount));
            eventC.setText(getDefinedEventCountText("C", eventCCount));
        } else {
            eventA.setText(getNumberedEventCountText(R.string.eventLabelA, eventACount));
            eventB.setText(getNumberedEventCountText(R.string.eventLabelB, eventBCount));
            eventC.setText(getNumberedEventCountText(R.string.eventLabelC, eventCCount));
        }

        totalEvents.setText(getNumberedEventCountText(R.string.totalEvents, sharedPreferencesHelper.getTotalCount() + ""));
    }

    private String getDefinedEventCountText(String counterId, String counterCount) {
        return sharedPreferencesHelper.getCounterName(counterId) + ": " + counterCount;
    }

    private String getNumberedEventCountText(int stringResource, String counterCount) {
        return getResources().getString(stringResource) + " " + counterCount;
    }
}