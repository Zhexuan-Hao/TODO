package com.example.todo.Firebase.RealtimeDatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.ui.home.HomeFragment;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Event> list = new ArrayList<>();
    public RVAdapter(Context ctx){
        this.context=ctx;
    }
    public void setItems(ArrayList<Event> events){
        list.addAll(events);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new EventVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Event e = null;
        this.onBindViewHolder(holder,position,e);

    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Event eve){
        EventVH vh = (EventVH) holder;
        Event event = eve==null? list.get(position):eve;
        vh.txt_name.setText(event.getName());
        vh.txt_time.setText(event.getTime());
        vh.txt_detail.setText(event.getDetail());
        vh.txt_status.setText(event.getStatus());
        vh.txt_option.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context,vh.txt_option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item ->{
                if(item.getItemId() == R.id.menu_edit){
                    Intent intent = new Intent(context, HomeFragment.class);
                    intent.putExtra("EDIT", event);
                    context.startActivity(intent);
                } else if(item.getItemId() == R.id.menu_remove){
                    DAOEvent dao = new DAOEvent();
                    dao.remove(event.getKey()).addOnSuccessListener(suc->{
                        Toast.makeText(context,"remove success!!!",Toast.LENGTH_SHORT).show();
                        //notifyItemChanged(position);
                    }).addOnFailureListener(er -> {
                        Toast.makeText(context,""+er.getMessage(),Toast.LENGTH_SHORT).show();
                    });
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
