package com.example.todo.RoomDatabase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {
    @PrimaryKey (autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    @NonNull
    public String name;

    @ColumnInfo(name = "detail")
    @NonNull
    public String detail;

    @ColumnInfo(name = "status")
    @NonNull
    public String status;

    public Event(@NonNull String name, @NonNull String detail, @NonNull String status) {
        this.name = name;
        this.detail = detail;
        this.status = status;
    }
}
