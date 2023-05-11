package com.example.todo.RoomDatabase.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todo.RoomDatabase.dao.EventDAO;
import com.example.todo.RoomDatabase.database.EventDatabase;
import com.example.todo.RoomDatabase.entity.Event;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class EventRepository {
    private EventDAO eventDao;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application application, String email){
        EventDatabase db = EventDatabase.getInstance(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAll(email);
    }

    public LiveData<List<Event>> getAllEvents(){
        return allEvents;
    }

    public void insert(final Event event){
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventDao.insert(event);
            }
        });
    }

    public void deleteAll(){
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventDao.deleteAll();
            }
        });
    }

    public void delete(final Event event){
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventDao.delete(event);
            }
        });
    }

    public void updateEvent(final Event event){
        EventDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventDao.updateEvent(event);
            }
        });
    }

    public CompletableFuture<Event> findByIDFuture(final int eventId, String email){
        return CompletableFuture.supplyAsync(new Supplier<Event>() {
            @Override
            public Event get() {
                return eventDao.findByID(eventId, email);
            }
        }, EventDatabase.databaseWriteExecutor);
    }
}
