package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todo.Retrofit.RetrofitClient;
import com.example.todo.Retrofit.Weather.Constant;
import com.example.todo.Retrofit.Weather.WeatherResponse;
import com.example.todo.Retrofit.Weather.WeatherService;
import com.example.todo.databinding.ActivityWelcomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomeBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = WelcomeActivity.this;

        WeatherService weatherService = RetrofitClient.getRetrofitInstance(Constant.BASE_URL).create(WeatherService.class);

        Call<WeatherResponse> callWeather = weatherService.getWeather(33.44, -94.04, Constant.KEY);

        callWeather.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    binding.WelcomeTempEdt.setText("Temperature: " + weatherResponse.getMain().getTemp());
                    binding.WelcomeWeatherEdt.setText("Weather: " + weatherResponse.getWeather()[0].getMain());
                } else {
                    Toast.makeText(context ,"Fail to get weather", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(context ,"Fail to get weather", Toast.LENGTH_SHORT);
            }
        });


        binding.WelcomeStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}