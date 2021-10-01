package com.example.kawwas_ass1.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;
import com.example.kawwas_ass1.database.entity.Event;

@Dao
public interface EventDAO {
    @Query("SELECT * FROM event_table")
    List<Event> getAllEvents();

    @Query("SELECT COUNT(*) FROM event_table WHERE event_name=:eventName")
    int getTotalEventsByName(String eventName);

    @Query("SELECT COUNT(*) FROM event_table")
    int getTotalEvents();

    // Get Table of Events and Occurences
    // @Query("SELECT event_name, COUNT(*) FROM event_table GROUP BY event_name")
    // List<Event> getTotalEvents();

//    @Delete
//    void delete(Event event);
}
