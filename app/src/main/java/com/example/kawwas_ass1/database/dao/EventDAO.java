package com.example.kawwas_ass1.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import com.example.kawwas_ass1.database.entity.Event;

@Dao
public interface EventDAO {
    // Get List of Events from Table
    @Query("SELECT event_num FROM event_table")
    List<String> getEventsListByNum();

    @Query("SELECT event_name FROM event_table")
    List<String> getEventsListByName();

    // Get Events Total and Individual Count
    @Query("SELECT COUNT(*) FROM event_table")
    int getTotalEventsCount();

    @Query("SELECT COUNT(*) FROM event_table WHERE event_num=:eventNum")
    int getEventsCountByNum(String eventNum);

    // Insert & Delete Events
    @Insert
    void insertEvent(Event... events);

    @Query("DELETE FROM event_table")
    void clearTable();
}
