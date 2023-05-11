package com.example.todo.Room.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.todo.Room.Converter.DateConverter;
import com.example.todo.Room.Dao.EventDao;
import com.example.todo.Room.Entity.Event;

@Database(entities = {Event.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class EventDatabase extends RoomDatabase {

    public abstract EventDao eventDao();
}
