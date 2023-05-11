package com.example.todo.Firebase.RealtimeDatabase;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Event implements Serializable {
    @Exclude
    private String key;
    private String name;
    private String time;
    private String detail;
    private String status;
    private String email;

    public Event() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Event(String name, String time, String detail, String status, String email) {
        this.name = name;
        this.time = time;
        this.detail = detail;
        this.status = status;
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
