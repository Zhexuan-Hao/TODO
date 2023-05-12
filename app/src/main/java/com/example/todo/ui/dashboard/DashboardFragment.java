package com.example.todo.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.todo.MyAdapter;
import com.example.todo.R;
import com.example.todo.Room.Adapter.EventAdapter;
import com.example.todo.Room.Dao.EventDao;
import com.example.todo.Room.Database.EventDatabase;
import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.ViewModel.EventViewModel;
import com.example.todo.databinding.FragmentDashboardBinding;
import com.example.todo.ui.add.AddFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private LiveData<List<Event>> eventList;

    private EventViewModel eventViewModel;

    private FirebaseUser user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();



        RecyclerView recyclerView = binding.recyclerView;

        final EventAdapter adapter = new EventAdapter(new EventAdapter.EventDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get a new or existing ViewModel from the ViewModelProvider.
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        eventViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(events);
        });
//        MyAdapter adapter = new MyAdapter(eventList.getValue());
//        recyclerView.setAdapter(adapter);

//        SwipeRefreshLayout swipeRefreshLayout = binding.swipeRefreshLayout;
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // 执行下拉刷新操作
//                adapter.notifyDataSetChanged();
//                eventList = getData();
//                swipeRefreshLayout.setRefreshing(false); // 关闭下拉刷新动画
//            }
//        });

        binding.iconFunctionAdd.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(root);
            navController.navigate(R.id.action_nav_dashboard_to_nav_add);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    public LiveData<List<Event>> getData() {
//        MutableLiveData<List<Event>> data = new MutableLiveData<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                EventDatabase eventDatabase = Room.databaseBuilder(getContext(),
//                        EventDatabase.class, "EventDatabase").build();
//                EventDao eventDao = eventDatabase.eventDao();
//                List<Event> events = eventDao.selectEventByUserId(user.getUid());
//
//                // 在background thread中更新LiveData数据
//                data.postValue(events);
//            }
//        }).start();
//
//        // 返回LiveData对象
//        return data;
//    }


//    public List<Event> getData() {
//        EventDatabase eventDatabase = Room.databaseBuilder(getContext(),
//                EventDatabase.class, "EventDatabase").build();
//        EventDao eventDao = eventDatabase.eventDao();
//        List<Event> events = eventDao.selectEventByUserId(user.getUid());
//        return events;
//    }

//    public LiveData<List<Event>> getData() {
//        MutableLiveData<List<Event>> eventData = new MutableLiveData<>();
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                EventDatabase eventDatabase = Room.databaseBuilder(getContext(),
//                        EventDatabase.class, "EventDatabase").build();
//                EventDao eventDao = eventDatabase.eventDao();
//                List<Event> events = eventDao.selectEventByUserId(user.getUid());
//
//                if(events.size() == 0) {
//                    events = new ArrayList<>();
//                }
//                eventData.postValue(events);
//                List<Event> value = eventData.getValue();
//                System.out.println(1);
//                // 或使用eventData.setValue(events)，如果确定已经在非UI线程中调用了方法
//            }
//        });
//        List<Event> value = eventData.getValue();
//        return eventData;
//    }



}
