package com.banquemisr.www.bmmedical.ui.transaction;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;

public class TransactionDetailsViewModel extends ViewModel {
    AppRepository appRepository;
    LiveData<MedicalEntity> medicalEntity;
    LiveData<User> user;
    LiveData<RequestDetails> transActionDetails;

    public TransactionDetailsViewModel(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public LiveData<RequestDetails> getTransactionDetailsById(String id) {
        return appRepository.getRequestById(id);
    }

    public LiveData<MedicalEntity> getMedicalEntity(String id) {
        return appRepository.getEntityById(id);
    }

    public LiveData<User> getUser(int oracle) {
        return appRepository.getUserByOracleNoIni(oracle);
    }
}
