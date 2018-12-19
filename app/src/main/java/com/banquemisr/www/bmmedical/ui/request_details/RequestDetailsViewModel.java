package com.banquemisr.www.bmmedical.ui.request_details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class RequestDetailsViewModel extends ViewModel {
    public LiveData<MedicalEntity> medicalEntity;
    AppRepository appRepository;

    public RequestDetailsViewModel(AppRepository appRepository, String id) {
        this.appRepository = appRepository;
        medicalEntity = appRepository.getEntityByID(id);
    }


}
