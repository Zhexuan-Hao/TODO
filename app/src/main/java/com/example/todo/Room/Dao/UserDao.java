package com.example.todo.Room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todo.Room.Entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from user")
    public List<User> selectAllUsers();

    @Query("select * from user where user_id = (:userId)")
    public User selectUserById(String userId);

    @Update
    public void updateUserById(User user);

    @Insert
    public void insertUser(User user);

    @Delete
    public void deleteUser(User user);


}
