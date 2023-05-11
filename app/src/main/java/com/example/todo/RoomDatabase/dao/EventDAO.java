package com.example.todo.RoomDatabase.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todo.RoomDatabase.entity.Event;

import java.util.List;

@Dao
public interface EventDAO {
    @Query("SELECT * FROM event WHERE email = :email ORDER BY id ASC")
    LiveData<List<Event>> getAll(String email);

    @Query("SELECT * FROM event WHERE id = :eventId and email = :email LIMIT 1")
    Event findByID(int eventId, String email);

    @Insert
    void insert(Event event);

    @Delete
    void delete(Event event);

    @Update
    void updateEvent(Event event);

    @Query("DELETE FROM event")
    void deleteAll();
}
