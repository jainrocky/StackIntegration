package com.example.rockyjain.stackintegration;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createUser(Users users);

    @Query("DELETE FROM users")
    void emptyTable();

    @Query("SELECT * FROM users")
    List<Users> getUser();

}
