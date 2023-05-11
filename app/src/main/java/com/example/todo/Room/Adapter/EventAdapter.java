package com.example.todo.Room.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.ViewHolder.EventViewHolder;

public class EventAdapter extends ListAdapter<Event, EventViewHolder> {

    public EventAdapter(@NonNull DiffUtil.ItemCallback<Event> diffCallback) {
        super(diffCallback);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EventViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event current = getItem(position);
        holder.bindData(current);
    }

    public static class EventDiff extends DiffUtil.ItemCallback<Event> {

        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getContent().equals(newItem.getContent()) && oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getStatus() == newItem.getStatus() && oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getLatitude().equals(newItem.getLatitude()) && oldItem.getLongitude().equals(newItem.getLongitude());
        }
    }


}
