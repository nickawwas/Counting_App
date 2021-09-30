package com.example.kawwas_ass1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
}