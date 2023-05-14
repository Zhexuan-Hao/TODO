package com.example.todo.Room.Adapter;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.Room.Entity.Event;
//import com.example.todo.Room.ViewHolder.EventViewHolder;
import com.example.todo.Room.ViewModel.EventViewModel;
import com.example.todo.ui.dashboard.DashboardFragment;
import com.example.todo.ui.dashboard.DashboardFragmentDirections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {

    private EventViewModel eventViewModel;

    public EventAdapter(@NonNull DiffUtil.ItemCallback<Event> diffCallback, EventViewModel eventViewModel) {
        super(diffCallback);
        this.eventViewModel = eventViewModel;
    }

    public EventAdapter(@NonNull DiffUtil.ItemCallback<Event> diffCallback) {
        super(diffCallback);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = getItem(position);
        holder.nameTextView.setText(event.getTitle());
        String pattern = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        String formattedDate = dateFormat.format(event.getDate());
        holder.dateTextView.setText(formattedDate);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(event.getStatus() == 1);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    event.setStatus(1);
                } else {
                    event.setStatus(0);
                }
                eventViewModel.update(event);
            }
        });

        holder.deleteButton.setOnClickListener(null);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.dialog_layout, null);  // 获取对话框布局文件
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();

                // 获取对话框中的控件并设置文本
                TextView dialogTextView = dialogView.findViewById(R.id.dialog_textview);
                dialogTextView.setText("Are you sure you want to delete " + event.getTitle() + "?");  // 根据RecyclerView的内容设置对话框文本内容

                // 设置“取消”按钮单击事件监听器
                Button cancelButton = dialogView.findViewById(R.id.dialog_cancel_button);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();  // 隐藏对话框
                    }
                });

                // 设置“确定”按钮单击事件监听器
                Button okButton = dialogView.findViewById(R.id.dialog_ok_button);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eventViewModel.delete(event);  // 执行删除操作
                        alertDialog.dismiss();  // 隐藏对话框
                    }
                });

                alertDialog.show();
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = DashboardFragmentDirections.actionNavDashboardToNavAdd(event);
                NavController navController = Navigation.findNavController(holder.itemView);
                navController.navigate(action);
            }
        });

//        Event current = getItem(position);
//        holder.bindData(current);
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

    class EventViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView nameTextView;
        TextView dateTextView;

        Button deleteButton;

        CardView cardView;

        public EventViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.Dashboard_Isfinish_Cb);
            nameTextView = itemView.findViewById(R.id.Dashboard_Title_Tv);
            dateTextView = itemView.findViewById(R.id.Dashboard_Date_Tv);
            deleteButton = itemView.findViewById(R.id.Dashboard_Delete_Btn);
            cardView = itemView.findViewById(R.id.cardView);

        }


    }


}
