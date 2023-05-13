package com.example.todo.ui.detail;


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
import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.ViewModel.EventViewModel;
import com.example.todo.databinding.FragmentDetailBinding;

import java.util.Calendar;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;

    private EventViewModel eventViewModel;
    private Event event;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(getArguments() != null) {
            DetailFragmentArgs detailFragmentArgs = DetailFragmentArgs.fromBundle(getArguments());
            if (detailFragmentArgs.getEvent() != null) {
                event = detailFragmentArgs.getEvent();
            }
        }

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        if(event != null) {
            binding.DetailTitleEdt.setText(event.getTitle());
            binding.DetailContentEdt.setText(event.getContent());
            binding.DetailCalendarView.setDate(event.getDate().getTime());
            binding.DetailIsfinishCb.setChecked(event.getStatus() == 1);
            binding.DetailLocationEdt.setText(event.getAddress());
        }

        binding.DetailLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        binding.DetailSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setTitle(binding.DetailTitleEdt.getText().toString());
                event.setContent(binding.DetailContentEdt.getText().toString());
                if(binding.DetailIsfinishCb.isChecked()) {
                    event.setStatus(1);
                } else {
                    event.setStatus(0);
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(binding.DetailCalendarView.getDate());
                event.setDate(calendar.getTime());
                eventViewModel.update(event);

                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.action_nav_detail_to_nav_dashboard);
            }
        });

        binding.DetailCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(root);
                navController.navigate(R.id.action_nav_detail_to_nav_dashboard);

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