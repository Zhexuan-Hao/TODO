package com.example.todo;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Room.Entity.Event;

public class MyViewHolder extends RecyclerView.ViewHolder {
    CheckBox checkBox;
    TextView nameTextView;
    TextView dateTextView;

    public MyViewHolder(View itemView) {
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
}
