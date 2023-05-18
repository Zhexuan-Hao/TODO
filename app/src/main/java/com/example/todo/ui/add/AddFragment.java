package com.example.todo.ui.add;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.todo.R;
import com.example.todo.Retrofit.RetrofitClient;
import com.example.todo.Retrofit.Weather.Constant;
import com.example.todo.Retrofit.Weather.WeatherResponse;
import com.example.todo.Retrofit.Weather.WeatherService;
import com.example.todo.Room.Entity.Event;
import com.example.todo.Room.ViewModel.EventViewModel;
import com.example.todo.WorkManager.MyWorker;
import com.example.todo.databinding.FragmentAddBinding;
import com.example.todo.ui.map.LocationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    private EventViewModel eventViewModel;

    private FirebaseUser user;

    private Event event;
    private WeatherService weatherService;
    private Call<WeatherResponse> callWeather;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        weatherService = RetrofitClient.getRetrofitInstance(Constant.BASE_URL).create(WeatherService.class);

        if(getArguments() != null) {
            AddFragmentArgs addFragmentArgs = AddFragmentArgs.fromBundle(getArguments());
            if (addFragmentArgs.getEvent() != null) {
                event = addFragmentArgs.getEvent();
                binding.AddTitleEdt.setText(event.getTitle());
                binding.AddContentEdt.setText(event.getContent());
                binding.AddLocationEdt.setText(event.getAddress());
                binding.AddIsfinishCb.setChecked(event.getStatus() == 1);
                if(event.getDate() != null) {
                    binding.AddCalendarView.setDate(event.getDate().getTime());
                }
                if (event.getAddress() != null) {
                    callWeather = weatherService.getWeather(event.getLatitude(), event.getLongitude(), Constant.KEY);
                    callWeather.enqueue(new Callback<WeatherResponse>() {
                        @Override
                        public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                            if (response.isSuccessful()) {
                                WeatherResponse weatherResponse = response.body();
                                DecimalFormat df=new DecimalFormat("0.0");
                                Double K = Double.parseDouble(weatherResponse.getMain().getTemp())-273.15;
                                String temp = df.format(K);
                                binding.AddTemperatureEdt.setText(temp + "°C");
                                binding.AddWeatherEdt.setText(weatherResponse.getWeather()[0].getMain());
                            } else {
                                Toast.makeText(getContext() ,"Fail to get weather", Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Call<WeatherResponse> call, Throwable t) {
                            Toast.makeText(getContext() ,"Fail to get weather", Toast.LENGTH_SHORT);
                        }
                    });
                }
                if(event.getUser_id() != null) {
                    binding.AddName.setText("Edit Event");
                } else {
                    binding.AddName.setText("Add Event");
                }
            } else {
                event = new Event();
                binding.AddName.setText("Add Event");
            }
        } else {
            event = new Event();
            binding.AddName.setText("Add Event");
        }

        if (event.getUser_id() != null) {
            binding.AddIsfinishCb.setVisibility(View.VISIBLE);
        } else {
            binding.AddIsfinishCb.setVisibility(View.GONE);
        }

        binding.AddCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year); // year是int类型的年份
                calendar.set(Calendar.MONTH, month); // month是int类型的月份，需要减一
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth); // dayOfMonth是int类型的日期
                Date date = calendar.getTime();
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
                OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
                WorkManager.getInstance().enqueue(request);
                WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId())
                        .observe((LifecycleOwner) getActivity(), new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(@Nullable WorkInfo workInfo) {
                                String state = workInfo.getState().name();
                            }
                        });
                String title = binding.AddTitleEdt.getText().toString().trim();
                String content = binding.AddContentEdt.getText().toString().trim();
                String location = binding.AddLocationEdt.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty() || location.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Alert");
                    builder.setMessage("Title, content and location cannot be empty");
                    builder.setPositiveButton("Confirm", null);
                    builder.show();
                }

                else if (Character.isDigit(title.charAt(0))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Alert");
                    builder.setMessage("Title can not start with number");
                    builder.setPositiveButton("Confirm", null);
                    builder.show();
                }

                else {
                    event.setTitle(binding.AddTitleEdt.getText().toString());
                    event.setContent(binding.AddContentEdt.getText().toString());
                    event.setAddress(binding.AddLocationEdt.getText().toString());
                    if(event.getDate() == null) {
                        event.setDate(new Date());
                    }
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
