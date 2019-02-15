package com.banquemisr.www.bmmedical.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.util.Log;

import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;

import java.util.List;

@Dao
public interface EntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MedicalEntity medicalEntity);

    @Query("DELETE FROM entities")
    void deleteALL();

    @Query("SELECT * FROM entities ORDER BY name ASC")
    DataSource.Factory<Integer,MedicalEntity> getEntities();

    @Query("SELECT * FROM entities WHERE region = :region AND type = :entityType AND specialist = :specialization ORDER BY name ASC")
    DataSource.Factory<Integer,MedicalEntity> getFilteredEntities(String region, String entityType, String specialization);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<MedicalEntity> entityList);

    @Query("SELECT * FROM entities WHERE name LIKE :searchText ORDER BY name ASC")
    DataSource.Factory<Integer,MedicalEntity> getEntitiesBySearch(String searchText);

    @Query("SELECT * FROM entities WHERE id = :id")
    LiveData<MedicalEntity> getEntityByID(String id);

    @Query("SELECT * FROM entities WHERE type = :type AND specialist = :specializationByPosition ORDER BY name ASC")
    DataSource.Factory<Integer,MedicalEntity> getEntitiesByFilter1and2WithSpecialization(String type ,String specializationByPosition);

    @Query("SELECT * FROM entities WHERE type = :type ORDER BY name ASC")
    DataSource.Factory<Integer,MedicalEntity> getEntitiesByFilter1and2(String type);

    @Query("SELECT * FROM entities WHERE specialist = :specializationByPosition ORDER BY name ASC")
    DataSource.Factory<Integer,MedicalEntity> getEntitiesByFilter3(String specializationByPosition);
}
