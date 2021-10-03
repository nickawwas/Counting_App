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

    @ColumnInfo(name = "event_num")
    public String event_num;

    public Event(){};

    public Event(int eventId, String eventName, String eventNum) {
        event_id = eventId;
        event_name = eventName;
        event_num = eventNum;
    }
}
