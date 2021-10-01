package com.example.kawwas_ass1.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.kawwas_ass1.database.entity.Event;

//@Database(entities = { Event.class }, version = 1)
public abstract class AppDB extends RoomDatabase {
    protected AppDB(){};

    //TODO: Setup DB in Video 13-14
}
