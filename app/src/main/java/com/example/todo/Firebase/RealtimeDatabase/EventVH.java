package com.example.todo.Firebase.RealtimeDatabase;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;

public class EventVH extends RecyclerView.ViewHolder {
    public TextView txt_name,txt_time,txt_detail,txt_status;
    public TextView txt_option;
    public EventVH(@NonNull View itemView) {
        super(itemView);
        txt_name = itemView.findViewById(R.id.txt_name);
        txt_time = itemView.findViewById(R.id.txt_time);
        txt_detail = itemView.findViewById(R.id.txt_detail);
        txt_status = itemView.findViewById(R.id.txt_status);
        txt_option = itemView.findViewById(R.id.txt_option);
    }
}
