package com.banquemisr.www.bmmedical.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.show_approvals.model.Approval;

import java.util.List;

@Dao
public interface ApprovalsDao {

    @Query("SELECT * FROM approval")
    LiveData<List<Approval>> getApprovals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Approval> approvals);

    @Query("DELETE FROM approval")
    void deleteALL();
}
