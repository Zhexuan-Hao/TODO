package com.example.todo.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.todo.databinding.FragmentAddBinding;
import com.example.todo.databinding.FragmentMineBinding;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddFragment extends Fragment {

//    private FragmentAddBinding binding;
//    String name,time,detail,status,location;
//
//    @androidx.annotation.Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
//
//        binding = FragmentAddBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                String todayDate = year+"-"+month+"-"+dayOfMonth;
//                binding.time.setText(todayDate);
//            }
//        });
//
//        binding.locateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MappingActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        return root;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//
//        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                String todayDate = year+"-"+month+"-"+dayOfMonth;
//                binding.time.setText(todayDate);
//            }
//        });
//
//        binding.locateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MappingActivity.class);
//                startActivity(intent);
//            }
//        });
//        location = getIntent().getStringExtra("name");
//        binding.detail.setText(location);
//
//        List<String> list = new ArrayList<String>();
//        list.add("Finished!😀");
//        list.add("Not finished 😭");
//
//        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(HomeFragment.this, android.R.layout.simple_spinner_item,list);
//        binding.spinner.setAdapter(spinnerAdapter);
//        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String finishedOrNot = parent.getItemAtPosition(position).toString();
//                binding.status.setText(finishedOrNot);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//
//
//        DAOEvent dao = new DAOEvent();
//        Event event_edit = (Event) getIntent().getSerializableExtra("EDIT");
//        if(event_edit!=null){
//            binding.submitBtn.setText("UPDATE");
//            binding.name.setText(event_edit.getName());
//            binding.time.setText(event_edit.getTime());
//            binding.detail.setText(event_edit.getDetail());
//            binding.status.setText(event_edit.getStatus());
//            binding.listButton.setVisibility(View.GONE);
//            binding.newButton.setVisibility(View.GONE);
//            binding.mineButton.setVisibility(View.GONE);
//        } else {
//            binding.submitBtn.setText("SUBMIT");
//            binding.listButton.setVisibility(View.VISIBLE);
//            binding.newButton.setVisibility(View.VISIBLE);
//            binding.mineButton.setVisibility(View.VISIBLE);
//        }
//
//        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
//        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WorkManager.getInstance().enqueue(request);
//                Event event = new Event(binding.name.getText().toString(),
//                        binding.time.getText().toString(),
//                        binding.detail.getText().toString(),
//                        binding.status.getText().toString());
//                if(event_edit == null) {
//                    dao.add(event).addOnSuccessListener(suc -> {
//                        Toast.makeText(HomeFragment.this, "Record is inserted!", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(HomeFragment.this,DashboardFragment.class);
//                        startActivity(intent);
//                    }).addOnFailureListener(er -> {
//                        Toast.makeText(HomeFragment.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
//                    });
//                } else {
//                    HashMap<String, Object> hashMap = new HashMap<>();
//                    hashMap.put("name",binding.name.getText().toString());
//                    hashMap.put("time",binding.time.getText().toString());
//                    hashMap.put("detail",binding.detail.getText().toString());
//                    hashMap.put("status",binding.status.getText().toString());
//                    dao.update(event_edit.getKey(),hashMap).addOnSuccessListener(suc -> {
//                        Toast.makeText(HomeFragment.this,"Upadate success!!!",Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(HomeFragment.this,DashboardFragment.class);
//                        startActivity(intent);
//                    }).addOnFailureListener(er -> {
//                        Toast.makeText(HomeFragment.this,""+er.getMessage(),Toast.LENGTH_SHORT).show();
//                    });
//                }
//            }
//        });
//        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId())
//                .observe((LifecycleOwner) HomeFragment.this, new Observer<WorkInfo>() {
//                    @Override
//                    public void onChanged(@Nullable WorkInfo workInfo) {
//                        String state = workInfo.getState().name();
//                    }
//                });
//    }

}
