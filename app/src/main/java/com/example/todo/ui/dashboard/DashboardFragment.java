package com.example.todo.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Firebase.RealtimeDatabase.DAOEvent;
import com.example.todo.Firebase.RealtimeDatabase.Event;
import com.example.todo.Firebase.RealtimeDatabase.EventVH;
import com.example.todo.R;
import com.example.todo.databinding.FragmentDashboardBinding;
import com.example.todo.databinding.FragmentHomeBinding;
import com.example.todo.ui.add.AddFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;

public class DashboardFragment extends Fragment {

//    private FragmentDashboardBinding binding;
//    //RVAdapter adapter;
//    DAOEvent dao;
//    FirebaseRecyclerAdapter adapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentDashboardBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        binding.rv.setHasFixedSize(true);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        binding.rv.setLayoutManager(manager);
//        //adapter = new RVAdapter(this);
//        //binding.rv.setAdapter(adapter);
//        dao = new DAOEvent();
//        FirebaseRecyclerOptions<Event> option =
//                new FirebaseRecyclerOptions.Builder<Event>()
//                        .setQuery(dao.get(), new SnapshotParser<Event>() {
//                            @NonNull
//                            @Override
//                            public Event parseSnapshot(@NonNull DataSnapshot snapshot) {
//                                Event event = snapshot.getValue(Event.class);
//                                event.setKey(snapshot.getKey());
//                                return event;
//                            }
//                        }).build();
//
//        adapter = new FirebaseRecyclerAdapter(option) {
//            @Override
//            protected void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, Object o) {
//                EventVH vh = (EventVH) viewHolder;
//                Event event = (Event) o;
//                vh.txt_name.setText(event.getName());
//                vh.txt_time.setText(event.getTime());
//                vh.txt_detail.setText(event.getDetail());
//                vh.txt_status.setText(event.getStatus());
//                vh.txt_option.setOnClickListener(v -> {
//                    PopupMenu popupMenu = new PopupMenu(getContext(),vh.txt_option);
//                    popupMenu.inflate(R.menu.option_menu);
//                    popupMenu.setOnMenuItemClickListener(item ->{
//                        if(item.getItemId() == R.id.menu_edit){
//                            Bundle bundle = new Bundle();
//                            bundle.put("EDIT", "Hello, world!");
//
//                            Navigation.findNavController(root).navigate(R.id.action_nav_dashboard_to_nav_add);
//
//
//                            Intent intent = new Intent(DashboardFragment.this, AddFragment.class);
//                            intent.putExtra("EDIT", event);
//                            startActivity(intent);
//                        } else if(item.getItemId() == R.id.menu_remove){
//                            DAOEvent dao = new DAOEvent();
//                            dao.remove(event.getKey()).addOnSuccessListener(suc->{
//                                Toast.makeText(DashboardFragment.this,"remove success!!!",Toast.LENGTH_SHORT).show();
//                                //notifyItemChanged(position);
//                            }).addOnFailureListener(er -> {
//                                Toast.makeText(DashboardFragment.this,""+er.getMessage(),Toast.LENGTH_SHORT).show();
//                            });
//                        }
//                        return false;
//                    });
//                    popupMenu.show();
//                });
//            }
//
//            @NonNull
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(DashboardFragment.this).inflate(R.layout.layout_item,parent,false);
//                return new EventVH(view);
//            }
//
//            @Override
//            public void onDataChanged() {
//                Toast.makeText(DashboardFragment.this,"Data changed!",Toast.LENGTH_SHORT).show();
//            }
//        };
//        binding.rv.setAdapter(adapter);
//
//
//
//        return root;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = FragmentDashboardBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        binding.newButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(DashboardFragment.this, HomeFragment.class);
//                startActivity(intent);
//            }
//        });
//
//        binding.mineButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(DashboardFragment.this, NotificationsFragment.class);
//                startActivity(intent);
//            }
//        });
//
//        binding.rv.setHasFixedSize(true);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        binding.rv.setLayoutManager(manager);
//        //adapter = new RVAdapter(this);
//        //binding.rv.setAdapter(adapter);
//        dao = new DAOEvent();
//        FirebaseRecyclerOptions<Event> option =
//                new FirebaseRecyclerOptions.Builder<Event>()
//                        .setQuery(dao.get(), new SnapshotParser<Event>() {
//                            @NonNull
//                            @Override
//                            public Event parseSnapshot(@NonNull DataSnapshot snapshot) {
//                                Event event = snapshot.getValue(Event.class);
//                                event.setKey(snapshot.getKey());
//                                return event;
//                            }
//                        }).build();
//
//        adapter = new FirebaseRecyclerAdapter(option) {
//            @Override
//            protected void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, Object o) {
//                EventVH vh = (EventVH) viewHolder;
//                Event event = (Event) o;
//                vh.txt_name.setText(event.getName());
//                vh.txt_time.setText(event.getTime());
//                vh.txt_detail.setText(event.getDetail());
//                vh.txt_status.setText(event.getStatus());
//                vh.txt_option.setOnClickListener(v -> {
//                    PopupMenu popupMenu = new PopupMenu(DashboardFragment.this,vh.txt_option);
//                    popupMenu.inflate(R.menu.option_menu);
//                    popupMenu.setOnMenuItemClickListener(item ->{
//                        if(item.getItemId() == R.id.menu_edit){
//                            Intent intent = new Intent(DashboardFragment.this, HomeFragment.class);
//                            intent.putExtra("EDIT", event);
//                            startActivity(intent);
//                        } else if(item.getItemId() == R.id.menu_remove){
//                            DAOEvent dao = new DAOEvent();
//                            dao.remove(event.getKey()).addOnSuccessListener(suc->{
//                                Toast.makeText(DashboardFragment.this,"remove success!!!",Toast.LENGTH_SHORT).show();
//                                //notifyItemChanged(position);
//                            }).addOnFailureListener(er -> {
//                                Toast.makeText(DashboardFragment.this,""+er.getMessage(),Toast.LENGTH_SHORT).show();
//                            });
//                        }
//                        return false;
//                    });
//                    popupMenu.show();
//                });
//            }
//
//            @NonNull
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(DashboardFragment.this).inflate(R.layout.layout_item,parent,false);
//                return new EventVH(view);
//            }
//
//            @Override
//            public void onDataChanged() {
//                Toast.makeText(DashboardFragment.this,"Data changed!",Toast.LENGTH_SHORT).show();
//            }
//        };
//        binding.rv.setAdapter(adapter);
//
//
//        //loadData();
//                /*
//        binding.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                //super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.rv.getLayoutManager();
//                int totalItem = linearLayoutManager.getItemCount();
//                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                if(totalItem < lastVisible +3){
//                    if(!isLoading){
//                        isLoading = true;
//                        loadData();
//                    }
//                }
//            }
//        });
//    }
//
//    private void loadData(){
//        binding.swip.setRefreshing(true);
//        dao.get(key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<Event> events = new ArrayList<>();
//                for(DataSnapshot data: snapshot.getChildren()){
//                    Event event = data.getValue(Event.class);
//                    event.setKey(data.getKey());
//                    events.add(event);
//                    key = data.getKey();;
//                }
//                adapter.setItems(events);
//                adapter.notifyDataSetChanged();
//                isLoading = false;
//                binding.swip.setRefreshing(false);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                binding.swip.setRefreshing(false);
//            }
//        });
//
//                 */
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }

}
