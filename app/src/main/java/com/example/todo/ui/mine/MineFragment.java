package com.example.todo.ui.mine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.todo.R;
import com.example.todo.RealtimeDatabase.EventService;
import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.Entity.User;
import com.example.todo.Room.ViewModel.EventViewModel;
import com.example.todo.WelcomeActivity;
import com.example.todo.databinding.FragmentMineBinding;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



public class MineFragment extends Fragment {

    private EditText Email, Nickname;
    private FirebaseUser user;
    private EventViewModel eventViewModel;
    private LiveData<List<Event>> eventList;
    private List<Event> localEvents;

    private FragmentMineBinding binding;

    private EventService eventService = new EventService();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Email = binding.MineEmailEdt;
        Nickname = binding.MineNicknameEdt;


        user = FirebaseAuth.getInstance().getCurrentUser();
        Nickname.setText(user.getDisplayName());
        Email.setText(user.getEmail());
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        eventList = eventViewModel.getEvents();
        eventViewModel.getEvents().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                localEvents = events;
            }
        });

        binding.MineNicknameChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                // 获取LayoutInflater对象，并使用inflate方法从XML布局文件中创建视图
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_nickname, null);

                // 获取对话框中的EditText并设置其文字为当前用户名
                final EditText editText = dialogView.findViewById(R.id.nicknameEditText);
                editText.setText(user.getDisplayName());

                // 设置对话框中的视图
                alertDialogBuilder.setView(dialogView);

                // 设置PositiveButton点击事件
                alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 更新用户名EditText中的文字
                        Nickname.setText(editText.getText().toString().trim());

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(editText.getText().toString().trim())
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Username updated!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

//                        Toast.makeText(getContext(), "Username updated!", Toast.LENGTH_SHORT).show();
                    }
                });

                // 设置NegativeButton点击事件
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 取消对话框
                        dialog.cancel();
                    }
                });

                // 创建AlertDialog对象并显示
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        binding.MineUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventService.deleteAll(user.getUid());
                for (Event event : localEvents) {
                    eventService.insertEvent(event, user.getUid());
                }
            }
        });

        binding.MineDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventViewModel.deleteAll(user.getUid());
                databaseReference.child("event").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            for (DataSnapshot child : task.getResult().getChildren()) {
                                HashMap o = (HashMap) child.getValue();
                                if(o != null) {
                                    Event event = new Event();
                                    if(o.get("address") != null) {
                                        event.setAddress((String)o.get("address"));
                                    }
                                    if(o.get("date") != null) {
                                        Date date = new Date((Long)(((HashMap)o.get("date")).get("time")));
                                        event.setDate(date);
                                    }
                                    if(o.get("title") != null) {
                                        event.setTitle((String)o.get("title"));
                                    }
                                    if(o.get("status") != null) {
                                        event.setStatus(((Long)o.get("status")).intValue());
                                    }
                                    if(o.get("event_id") != null) {
                                        event.setEvent_id(((Long)o.get("event_id")).intValue());
                                    }
                                    if(o.get("content") != null) {
                                        event.setContent((String)o.get("content"));
                                    }
                                    if(o.get("user_id") != null) {
                                        event.setUser_id((String)o.get("user_id"));
                                    }
                                    eventViewModel.insert(event);
                                }
                            }

                        }
                    }
                });
            }
        });

        binding.MineStatisticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.action_nav_mine_to_nav_statistic);
            }
        });

        binding.MineLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(getContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void RemoveToken() {
        // 获取SharedPreferences对象
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }

}

