package com.example.todo.RoomDatabase.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todo.RoomDatabase.entity.Event;
import com.example.todo.RoomDatabase.repository.EventRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EventViewModel extends AndroidViewModel {
    private EventRepository eRepository;
    private LiveData<List<Event>> allEvents;

    public EventViewModel(Application application, String email){
        super(application);
        eRepository = new EventRepository(application, email);
        allEvents = eRepository.getAllEvents();
    }

    public CompletableFuture<Event> findByIDFuture(final int eventId, String email){
        return eRepository.findByIDFuture(eventId, email);
    }

    public LiveData<List<Event>> getAllEvents(){
        return allEvents;
    }

    public void insert(Event event){
        eRepository.insert(event);
    }

    public void deleteAll(){
        eRepository.deleteAll();
    }

    public void update(Event event){
        eRepository.updateEvent(event);
    }
}
