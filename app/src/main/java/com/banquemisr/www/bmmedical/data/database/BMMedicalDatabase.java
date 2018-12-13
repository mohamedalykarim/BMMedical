package com.banquemisr.www.bmmedical.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.login.model.User;


@Database(entities = {User.class, MedicalEntity.class}, version = 1)
public abstract class BMMedicalDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "com.banquemisr.www.bmmedical";

    private static final Object LOCK = new Object();
    private static BMMedicalDatabase sInstance;

    public static BMMedicalDatabase getsInstance(Context context){
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        BMMedicalDatabase.class, BMMedicalDatabase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract UserDao userDao();
    public abstract EntityDao entityDao();

}
