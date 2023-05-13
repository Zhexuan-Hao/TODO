package com.example.todo.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.todo.R;
import com.example.todo.RealtimeDatabase.EventService;
import com.example.todo.Room.Dao.EventDao;
import com.example.todo.Room.Database.EventDatabase;
import com.example.todo.Room.Entity.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyWorker extends Worker {
    private static final String TAG = "MyWorker";
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {
        // Get the Room database instance
        EventDatabase eventDatabase = EventDatabase.getDatabase(getApplicationContext());
        EventDao eventDao = eventDatabase.eventDao();

        // Get all events from the database
        List<Event> events = (List<Event>) eventDao.selectAllEvent();

        // Upload the events to Firebase Realtime database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");
        for (Event event : events) {
            databaseReference.child(event.getUser_id()).child(String.valueOf(event.getEvent_id())).setValue(event);
        }

        Log.d(TAG, "Events uploaded to Firebase Realtime database.");

        return Result.success();
    }

}
