package com.example.kawwas_ass1.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_table")
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int event_id;

    @ColumnInfo(name = "event_name")
    public String event_name;

    public Event(int eventId, String eventName) {
        event_id = eventId;
        event_name = eventName;
    }
}
