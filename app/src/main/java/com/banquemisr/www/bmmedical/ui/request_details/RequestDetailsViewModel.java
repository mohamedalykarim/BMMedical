package com.banquemisr.www.bmmedical.ui.request_details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.requests.model.Request;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RequestDetailsViewModel extends ViewModel {
    public LiveData<MedicalEntity> medicalEntity;
    public LiveData<List<RequestDetails>> requestsWithin15;

    AppRepository appRepository;
    public  MutableLiveData<String> benfituaryName;
    public MutableLiveData<Boolean> isExistTransformation;

    MutableLiveData<Boolean> pressMap;
    MutableLiveData<Boolean> pressAskForMedicalRequest;

    public RequestDetailsViewModel(AppRepository appRepository, String id) {
        this.appRepository = appRepository;
        medicalEntity = appRepository.getEntityById(id);
        pressMap = new MutableLiveData<>();
        pressAskForMedicalRequest = new MutableLiveData<>();
        benfituaryName = new MutableLiveData<>();
        isExistTransformation = new MutableLiveData<>();
    }


    public void onAskForMedicalRequestClicked(){
        pressAskForMedicalRequest.setValue(true);
    }

    public void onMapClicked(){
        pressMap.setValue(true);
    }

    public void addTheMedicalRequest(User user, Context context) {
        RequestDetails requestDetails = new RequestDetails();
        Date date = new Date();
        requestDetails.setDate(date);
        requestDetails.setContractorId(medicalEntity.getValue().getId());
        requestDetails.setSpecialization(medicalEntity.getValue().getSpecialist());
        requestDetails.setOracle(user.getOracle());
        requestDetails.setStatus("approved");
        requestDetails.setName(medicalEntity.getValue().getName());


        /**
         * Benfituary Name
         */
        if(null != benfituaryName.getValue()){
            requestDetails.setBenfituaryName(benfituaryName.getValue());
        }else {
            requestDetails.setBenfituaryName(context.getResources().getString(R.string.benfituary_name_self));
        }


        appRepository.addTheMedicalRequest(requestDetails);
    }

    public LiveData<List<RequestDetails>> getRequestsWithin15(String specialization) {
        return appRepository.getRequestsWithin15Days(specialization);

    }
}
