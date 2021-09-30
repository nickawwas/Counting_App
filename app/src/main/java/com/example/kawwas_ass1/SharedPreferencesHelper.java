package com.example.kawwas_ass1;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    public  SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("EventPreference", Context.MODE_PRIVATE);
    }

    // Update & Get Counter Names
    public void updateCounterName(String counterId, String newCounterName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("counterName" + counterId, newCounterName);
        editor.commit();
    }

    public String getCounterName(String counterId) {
        return sharedPreferences.getString("counterName" + counterId, null);
    }

    // Inc & Get Total Count
    public void incTotalCount() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int totalCount = getTotalCount();
        editor.putInt("totalCount", ++totalCount);
        editor.commit();
    }

    public int getTotalCount() {
        return sharedPreferences.getInt("totalCount", 0);
    }
}
