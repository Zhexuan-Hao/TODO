package com.example.todo.Room.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(tableName = "user")
public class User {

    @PrimaryKey
    @NonNull
    private String user_id;
    private String user_email;
    private String user_nickname;



}
