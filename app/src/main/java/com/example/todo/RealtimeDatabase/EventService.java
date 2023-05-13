package com.example.todo.RealtimeDatabase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.todo.Room.Entity.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventService {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public void insertEvent(Event event, String userId) {
        databaseReference.child("event").child(userId).child(Integer.toString(event.getEvent_id())).setValue(event);
    }

    public List<Event> selectEvent(String userId) {
        List<Event> eventList = new ArrayList<>();
        databaseReference.child("event").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    List<HashMap> value = (List<HashMap>) task.getResult().getValue();
                    for (HashMap o : value) {
                        if(o != null) {
                            Event event = new Event();
                            if(o.get("address") != null) {
                                event.setAddress((String)o.get("address"));
                            }
                            if(o.get("date") != null) {
                                Date date = new Date((Long)(((HashMap)o.get("date")).get("time")));
                                event.setDate(date);
                            }
                            if(o.get("title") != null) {
                                event.setTitle((String)o.get("title"));
                            }
                            if(o.get("status") != null) {
                                event.setStatus(((Long)o.get("status")).intValue());
                            }
                            if(o.get("event_id") != null) {
                                event.setEvent_id(((Long)o.get("event_id")).intValue());
                            }
                            if(o.get("content") != null) {
                                event.setContent((String)o.get("content"));
                            }
                            if(o.get("user_id") != null) {
                                event.setUser_id((String)o.get("user_id"));
                            }
                            eventList.add(event);
                        }
                    }
                }
            }
        });
        return eventList;
    }

    public void updateEvent(Event event) {
        databaseReference.child("event").child(Integer.toString(event.getEvent_id())).setValue(event);
    }

    public void deleteEvent(Event event) {
        databaseReference.child("event").child(Integer.toString(event.getEvent_id())).removeValue();
    }

    public void deleteAll(String userId) {
        databaseReference.child("event").child(userId).removeValue();
    }

}
