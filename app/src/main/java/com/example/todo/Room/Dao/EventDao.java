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

    @Query("select * from event_table")
    public LiveData<List<Event>> selectAllEvent();

    @Query("select * from event_table where event_id = (:eventId)")
    public Event selectEventById(int eventId);

    @Query("select * from event_table where user_id = (:userId)")
    public LiveData<List<Event>> selectEventByUserId(String userId);

    @Query("select * from event_table where user_id = (:userId) and status = 0")
    public LiveData<List<Event>> selectUnfinishedEventByUserId(String userId);

    @Query("select * from event_table where user_id = (:userId) and status = 1")
    public LiveData<List<Event>> selectFinishedEventByUserId(String userId);

    @Query("select count(*) from event_table where user_id = (:userId) and status = 0")
    public Integer countUnfinishedEventByUserId(String userId);

    @Query("select count(*) from event_table where user_id = (:userId) and status = 1")
    public Integer countFinishedEventByUserId(String userId);

    @Query("select * from event_table where user_id = (:userId) and date BETWEEN (:oneWeekAgoInt) AND (:todayInt)")
    public List<Event> selectOneWeekEventsByUserId(String userId, Integer oneWeekAgoInt, Integer todayInt);

    @Update
    public void updateEvent(Event event);

    @Insert
    public void insertEvent(Event event);

    @Delete
    public void deleteEvent(Event event);

    @Query("delete from event_table")
    public void deleteAllEvents();
}
