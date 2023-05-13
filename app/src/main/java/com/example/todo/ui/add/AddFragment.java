package com.example.todo.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    private EventViewModel eventViewModel;

    private FirebaseUser user;

    private Event event;

    private TextView locationTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EventAdapter adapter = new EventAdapter(new EventAdapter.EventDiff());
        user = FirebaseAuth.getInstance().getCurrentUser();
        locationTextView = binding.locationTextView;

        // Get a new or existing ViewModel from the ViewModelProvider.
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        if(getArguments() != null) {
            AddFragmentArgs addFragmentArgs = AddFragmentArgs.fromBundle(getArguments());
            if (addFragmentArgs.getEvent() != null) {
                event = addFragmentArgs.getEvent();
                binding.titleEditText.setText(event.getTitle());
                binding.contentEditText.setText(event.getContent());
                binding.locationTextView.setText(event.getAddress());
            } else {
                event = new Event();
            }
        } else {
            event = new Event();
        }

        binding.locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setTitle(binding.titleEditText.getText().toString());
                event.setContent(binding.contentEditText.getText().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(binding.calendarView.getDate());
                event.setDate(calendar.getTime());
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setTitle(binding.titleEditText.getText().toString());
                event.setContent(binding.contentEditText.getText().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(binding.calendarView.getDate());
                event.setDate(calendar.getTime());
                event.setUser_id(user.getUid());
                event.setStatus(0);
                eventViewModel.insert(event);

                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.action_nav_add_to_nav_dashboard);
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
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
