package com.example.rockyjain.stackintegration;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.net.Authenticator;

@Database(entities = {Users.class}, version = 1)
public abstract class StackDataBase extends RoomDatabase {
    private static StackDataBase instance;

    public abstract UserDao userDao();

    public static synchronized StackDataBase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    StackDataBase.class,
                    "stack_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
