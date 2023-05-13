package com.example.todo.Room.Repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todo.Room.Dao.EventDao;
import com.example.todo.Room.Database.EventDatabase;
import com.example.todo.Room.Entity.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class EventRepository {

    private EventDao eventDao;
    private LiveData<List<Event>> eventList;

    private FirebaseUser user;
    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public EventRepository(Application application) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        EventDatabase db = EventDatabase.getDatabase(application);
        eventDao = db.eventDao();
        eventList = eventDao.selectEventByUserId(user.getUid());
//        eventList = eventDao.selectAllEvent();
    }


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Event>> getEvents() {
        return eventList;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insertEvent(event);
        });
    }

    public void update(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.updateEvent(event);
        });
    }

    public void delete(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.deleteEvent(event);
        });
    }


    public void deleteAll(String userId) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.deleteAllEvents(userId);
        });
    }
}
