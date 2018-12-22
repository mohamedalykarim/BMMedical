package com.banquemisr.www.bmmedical.ui.request_details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class RequestDetailsViewModel extends ViewModel {
    public LiveData<MedicalEntity> medicalEntity;
    AppRepository appRepository;

    MutableLiveData<Boolean> pressMap;
    MutableLiveData<Boolean> pressAskForMedicalRequest;

    public RequestDetailsViewModel(AppRepository appRepository, String id) {
        this.appRepository = appRepository;
        medicalEntity = appRepository.getEntityByID(id);
        pressMap = new MutableLiveData<>();
        pressAskForMedicalRequest = new MutableLiveData<>();
    }


    public void onAskForMedicalRequestClicked(){
        pressAskForMedicalRequest.setValue(true);
    }

    public void onMapClicked(){
        pressMap.setValue(true);
    }

}
