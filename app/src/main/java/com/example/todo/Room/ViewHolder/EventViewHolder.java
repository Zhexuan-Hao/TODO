package com.example.todo.Room.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.Room.Entity.Event;

public class EventViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;
    TextView nameTextView;
    TextView dateTextView;

    public EventViewHolder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
    }

    public void bindData(Event event) {
        checkBox.setChecked(event.getStatus() == 1);
        nameTextView.setText(event.getTitle());
        dateTextView.setText(event.getDate().toString());
    }

    public static EventViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
        return new EventViewHolder(view);
    }

}
