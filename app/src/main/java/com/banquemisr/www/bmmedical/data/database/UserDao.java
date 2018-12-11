package com.banquemisr.www.bmmedical.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.banquemisr.www.bmmedical.ui.login.model.User;

@Dao
public interface UserDao{
    @Query("SELECT * FROM users WHERE oracle = :oracle")
    LiveData<User> getUserByOracle(String oracle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM users")
    void deleteALL();

    @Query("SELECT * FROM users")
    LiveData<User> getUser();
}
