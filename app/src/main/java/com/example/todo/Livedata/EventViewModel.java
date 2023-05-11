package com.example.todo.Livedata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todo.Room.Entity.Event;

import java.util.List;

public class EventViewModel extends ViewModel {

    private MutableLiveData<List<Event>> eventList;

    public EventViewModel(){
        eventList = new MutableLiveData<>();
    }

    public void setEvents(List<Event> events) {
        eventList.setValue(events);
    }
    public LiveData<List<Event>> getEvents() {
        return eventList;
    }

}
