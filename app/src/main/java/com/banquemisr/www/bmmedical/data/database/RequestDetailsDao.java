package com.banquemisr.www.bmmedical.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;

import java.util.List;

@Dao
public interface RequestDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<RequestDetails> entityList);

    @Query("SELECT * FROM requestdetails ORDER BY date ASC")
    LiveData<List<RequestDetails>> getRequestsDetails();

    @Query("SELECT * FROM requestdetails WHERE id = :id")
    LiveData<RequestDetails> getRequstDetailsById(String id);

    @Query("DELETE FROM requestdetails")
    void deleteALL();

    @Query("SELECT * FROM requestdetails WHERE date > :date AND specialization = :specialization")
    LiveData<List<RequestDetails>> getRequestsDetailsWithin15Days(long date, String specialization);
}
