package com.example.todo.RealtimeDatabase;

import androidx.annotation.NonNull;

import com.example.todo.Room.Entity.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventService {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public void insertEvent(Event event) {
        databaseReference.child("event").child(Integer.toString(event.getEvent_id())).setValue(event);
    }

    public List<Event> selectEvent(String userId) {
        List<Event> eventList = new ArrayList<>();
        databaseReference.child("event").orderByChild("user_id").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Event event = child.getValue(Event.class);
                    if(event != null) {
                        eventList.add(event);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

}
