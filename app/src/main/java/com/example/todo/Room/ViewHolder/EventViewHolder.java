package com.example.todo.Room.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.ViewModel.EventViewModel;

public class EventViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;
    TextView nameTextView;
    TextView dateTextView;

    Event event;


    public EventViewHolder(View itemView, EventViewModel eventViewModel) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.Dashboard_Isfinish_Cb);
        nameTextView = itemView.findViewById(R.id.Dashboard_Title_Tv);
        dateTextView = itemView.findViewById(R.id.Dashboard_Date_Tv);
//        checkBox.setOnCheckedChangeListener(null);

    }

    public void bindData(Event event) {
        checkBox.setChecked(event.getStatus() == 1);
        nameTextView.setText(event.getTitle());
        dateTextView.setText(event.getDate().toString());
        this.event = event;
    }

    public static EventViewHolder create(ViewGroup parent, EventViewModel eventViewModel) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
        return new EventViewHolder(view, eventViewModel);
    }

}
