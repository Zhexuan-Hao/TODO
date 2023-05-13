package com.example.todo.Room.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.Repository.EventRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository eventRepository;
    private final LiveData<List<Event>> eventList;


    public EventViewModel(Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        eventList = eventRepository.getEvents();
        List<Event> value = eventList.getValue();
        System.out.println(1);

    }

    public LiveData<List<Event>> getEvents() { return eventList; }

    public void insert(Event event) { eventRepository.insert(event); }

    public void update(Event event) {
        eventRepository.update(event);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }

    public void deleteAll(String userId) {
        eventRepository.deleteAll(userId);
    }

}
