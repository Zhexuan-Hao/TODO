package com.example.todo.Room.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todo.Room.Converter.DateConverter;
import com.example.todo.Room.Dao.UserDao;
import com.example.todo.Room.Entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
