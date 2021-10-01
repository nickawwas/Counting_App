package com.example.kawwas_ass1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    protected Button saveButton;
    protected EditText counterInputNameA, counterInputNameB, counterInputNameC, maxCountInput;
    protected SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferencesHelper = new SharedPreferencesHelper(SettingsActivity.this);

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

                int maxCountNum = Integer.parseInt(maxCountText);

                // Validate Inputs - Must Be Filled and Meet Criteria Provided
                if(counterTextA.isEmpty() || counterTextB.isEmpty() || counterTextC.isEmpty() || maxCountText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Must Fill All Input Fields!", Toast.LENGTH_LONG).show();
                } else if( maxCountNum < 5 || maxCountNum > 200) {
                    Toast.makeText(getApplicationContext(), "Max Count Must Be Between 5 & 200!", Toast.LENGTH_LONG).show();
                } else {
                    //Update Inputs If Criteria is Satisfied
                    sharedPreferencesHelper.updateCounterName("A", counterTextA);
                    sharedPreferencesHelper.updateCounterName("B", counterTextB);
                    sharedPreferencesHelper.updateCounterName("C", counterTextC);
                    sharedPreferencesHelper.updateMaxCount(maxCountNum);

                    counterInputNameA.setEnabled(false);
                    counterInputNameB.setEnabled(false);
                    counterInputNameC.setEnabled(false);
                    maxCountInput.setEnabled(false);
                }
            }
        });

        //Disabled Until User Enters Edit Mode
        // - User Must First Press Action Button in Action Bar
        // - If Counter Names Are Null, Default in Edit Mode

        // Add Validation to Counters
        // - At Most 20 Characters Long + Only Alphabet & Spaces
        // - Between 5 & 200 for Max Count + Only Numbers

        //If Counter Names are Null
        // - Show Placeholder Otherwise Show Saved Values

        // No Save Button in Display Mode, Only in Edit Mode
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Go To Settings If Counters Names are Null

        //  if(sharedPreferencesHelper.getCounterName("A") == null) {
        //      Toast.makeText(getApplicationContext(), "Please Enter a Name", Toast.LENGTH_SHORT).show();
        //  }
    }

    // Get Counter Input Name Text
    private String getInputText(EditText counterElement) {
        return counterElement.getText().toString();
    }
}