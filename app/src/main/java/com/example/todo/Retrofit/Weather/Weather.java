package com.example.todo.Retrofit.Weather;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("main")
    private String main;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
