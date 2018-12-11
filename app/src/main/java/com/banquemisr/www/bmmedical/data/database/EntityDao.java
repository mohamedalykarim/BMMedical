package com.banquemisr.www.bmmedical.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.banquemisr.www.bmmedical.ui.Requests.Model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.login.model.User;

@Dao
public interface EntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MedicalEntity medicalEntity);

    @Query("DELETE FROM entities")
    void deleteALL();

    @Query("SELECT * FROM entities")
    LiveData<MedicalEntity> getEntities();
}
