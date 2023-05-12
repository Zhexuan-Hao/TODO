package com.example.todo.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todo.Room.Adapter.EventAdapter;
import com.example.todo.Room.ViewModel.EventViewModel;
import com.example.todo.databinding.FragmentAddBinding;
import com.example.todo.databinding.FragmentHomeBinding;
import com.example.todo.databinding.FragmentMineBinding;
import com.example.todo.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    private EventViewModel eventViewModel;

    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = binding.getRoot();

        final EventAdapter adapter = new EventAdapter(new EventAdapter.EventDiff());

        // Get a new or existing ViewModel from the ViewModelProvider.
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        eventViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(events);
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
