package com.banquemisr.www.bmmedical.ui.request_details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.requests.model.Request;
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

    public void addTheMedicalRequest(User user) {
        RequestDetails requestDetails = new RequestDetails();
        requestDetails.setDate(System.currentTimeMillis());
        requestDetails.setContractorId(medicalEntity.getValue().getId());
        requestDetails.setUserOracle(user.getOracle());
        requestDetails.setStatus("approved");
        requestDetails.setName(medicalEntity.getValue().getName());

        appRepository.addTheMedicalRequest(requestDetails);
    }
}
