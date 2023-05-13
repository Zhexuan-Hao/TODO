package com.example.todo.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.todo.R;
import com.example.todo.Room.Adapter.EventAdapter;
import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.ViewModel.EventViewModel;
import com.example.todo.databinding.FragmentAddBinding;
import com.example.todo.ui.detail.DetailFragmentArgs;
import com.example.todo.ui.map.LocationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    private EventViewModel eventViewModel;

    private FirebaseUser user;

    private Event event;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        if(getArguments() != null) {
            AddFragmentArgs addFragmentArgs = AddFragmentArgs.fromBundle(getArguments());
            if (addFragmentArgs.getEvent() != null) {
                event = addFragmentArgs.getEvent();
                binding.AddTitleEdt.setText(event.getTitle());
                binding.AddContentEdt.setText(event.getContent());
                binding.AddLocationEdt.setText(event.getAddress());
                binding.AddIsfinishCb.setChecked(event.getStatus() == 1);
                binding.AddCalendarView.setDate(event.getDate().getTime());
            } else {
                event = new Event();
            }
        } else {
            event = new Event();
        }

        if (event.getUser_id() != null) {
            binding.AddIsfinishCb.setVisibility(View.VISIBLE);
        } else {
            binding.AddIsfinishCb.setVisibility(View.GONE);
        }

        binding.AddCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Date date = new Date();
                date.setYear(year);
                date.setMonth(month);
                date.setDate(dayOfMonth);
                event.setDate(date);
            }
        });

        binding.AddLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setTitle(binding.AddTitleEdt.getText().toString());
                event.setContent(binding.AddContentEdt.getText().toString());
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }
        });

        binding.AddSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setTitle(binding.AddTitleEdt.getText().toString());
                event.setContent(binding.AddContentEdt.getText().toString());
                event.setAddress(binding.AddLocationEdt.getText().toString());
                if(event.getUser_id() == null) {
                    event.setUser_id(user.getUid());
                    event.setStatus(0);
                    eventViewModel.insert(event);
                } else {
                    if (binding.AddIsfinishCb.isChecked()) {
                        event.setStatus(1);
                    } else {
                        event.setStatus(0);
                    }
                    eventViewModel.update(event);
                }

                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.action_nav_add_to_nav_dashboard);
            }
        });

        binding.AddCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.action_nav_add_to_nav_dashboard);
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
