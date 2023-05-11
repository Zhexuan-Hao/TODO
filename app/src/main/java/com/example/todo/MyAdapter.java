package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Room.Entity.Event;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Event> eventList;

    public MyAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.bindData(event);
    }

    @Override
    public int getItemCount() {
//        List<Event> value = eventList.getValue();
        return eventList.size();
    }
}
