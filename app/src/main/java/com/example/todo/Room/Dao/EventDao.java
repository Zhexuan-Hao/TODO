package com.example.todo.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.Entity.User;

import java.util.List;

@Dao
public interface EventDao {

    @Query("select * from event")
    public List<Event> selectAllEvent();

    @Query("select * from event where event_id = (:eventId)")
    public Event selectEventById(int eventId);

    @Query("select * from event where user_id = (:userId)")
    public List<Event> selectEventByUserId(String userId);

    @Update
    public void updateEventById(Event event);

    @Insert
    public void insertEvent(Event event);

    @Delete
    public void deleteEvent(Event event);
}
