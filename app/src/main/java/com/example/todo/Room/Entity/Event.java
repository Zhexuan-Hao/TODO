package com.example.todo.Room.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int event_id;
    private String user_id;
    private String title;
    private String content;
    private Date date;
    private Double longitude; //经度
    private Double latitude; //纬度
    private int status; //0 unfinished 1 finished

    public Event() {}

    @Ignore
    public Event(int event_id, String user_id, String title, String content, Date date, Double longitude, Double latitude, int status) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
