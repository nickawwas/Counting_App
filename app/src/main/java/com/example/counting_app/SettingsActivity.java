package com.example.counting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.counting_app.database.AppDB;

public class SettingsActivity extends AppCompatActivity {

    protected Button saveButton;
    protected EditText counterInputNameA, counterInputNameB, counterInputNameC, maxCountInput;
    protected SharedPreferencesHelper sharedPreferencesHelper;
    protected AppDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Add Buttons to Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferencesHelper = new SharedPreferencesHelper(SettingsActivity.this);

        // Connect to DB
        db = AppDB.getInstance(getApplicationContext());

        // Input Fields for Counters and Max Count
        counterInputNameA = findViewById(R.id.counterInput1);
        counterInputNameB = findViewById(R.id.counterInput2);
        counterInputNameC = findViewById(R.id.counterInput3);
        maxCountInput = findViewById(R.id.maxCountInput);

        //Create Object and Listener for Save Button
        saveButton = findViewById(R.id.saveCounterButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String counterTextA = getInputText(counterInputNameA);
                String counterTextB = getInputText(counterInputNameB);
                String counterTextC = getInputText(counterInputNameC);
                String maxCountText = getInputText(maxCountInput);

                // Validate Inputs - All Inputs Must Be Filled and Meet Criteria Provided
                //  - Be At Most 20 Characters (Only Alphabet and Spaces) for Counters' Name
                //  - Between 5 & 200 for Max Count Value (Only Numbers)
                //  - Update and Clear Data Only When Changes Have Been Made
                if(counterTextA.isEmpty() || counterTextB.isEmpty() || counterTextC.isEmpty() || maxCountText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Must Fill All Input Fields!", Toast.LENGTH_LONG).show();
                } else {
                    int maxCountNum = Integer.parseInt(maxCountText);

                    if (maxCountNum < 5 || maxCountNum > 200) {
                        Toast.makeText(getApplicationContext(), "Max Count Must Be Between 5 & 200!", Toast.LENGTH_LONG).show();
                    } else {
                        // Update Only if Data Was Changed
                        if(!counterTextA.equals(sharedPreferencesHelper.getCounterName("A")) || !counterTextB.equals(sharedPreferencesHelper.getCounterName("B")) || !counterTextC.equals(sharedPreferencesHelper.getCounterName("C")) || maxCountNum != sharedPreferencesHelper.getMaxCount()) {
                            //Update Inputs If Criteria is Satisfied
                            sharedPreferencesHelper.updateCounterName("A", counterTextA);
                            sharedPreferencesHelper.updateCounterName("B", counterTextB);
                            sharedPreferencesHelper.updateCounterName("C", counterTextC);
                            sharedPreferencesHelper.updateMaxCount(maxCountNum);

                            // Clear Table of Old Data
                            db.eventDAO().clearTable();

                            // App Initialization Complete
                            if(!sharedPreferencesHelper.getAppInitStatus())
                                sharedPreferencesHelper.setAppInitStatus();

                            // Settings Have Been Updated
                            sharedPreferencesHelper.setChangedSettingsState(true);
                            Toast.makeText(getApplicationContext(), "Changes Saved!", Toast.LENGTH_LONG).show();
                        }

                        // Make Inputs Uneditable
                        setInputsEditableState(false);
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // If App Not Initialized (Counters Not Yet Defined), Show Placeholder in Edit Mode, Otherwise Show Saved Values in Display Mode
        // Else If App is Initialized (Counters are Defined), Show Editable Values in Edit Mode, Otherwise Show Uneditable Values in Display Mode
        if(!sharedPreferencesHelper.getAppInitStatus()) {
            setInputsEditableState(true);
        } else {
            setInputsEditableState(false);

            counterInputNameA.setText(sharedPreferencesHelper.getCounterName("A"));
            counterInputNameB.setText(sharedPreferencesHelper.getCounterName("B"));
            counterInputNameC.setText(sharedPreferencesHelper.getCounterName("C"));
            maxCountInput.setText(sharedPreferencesHelper.getMaxCount() + "");
        }
    }

    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    // Select Option from Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editSettingsItem:
                setInputsEditableState(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Get Counter Input Name Text
    private String getInputText(EditText counterElement) {
        return counterElement.getText().toString();
    }

    // Disable/Enable Input Fields
    private void setInputsEditableState(boolean editState) {
        counterInputNameA.setEnabled(editState);
        counterInputNameB.setEnabled(editState);
        counterInputNameC.setEnabled(editState);
        maxCountInput.setEnabled(editState);
        saveButton.setEnabled(editState);
    }
}