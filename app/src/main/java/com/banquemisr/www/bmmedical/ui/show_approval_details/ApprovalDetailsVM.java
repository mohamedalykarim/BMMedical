package com.banquemisr.www.bmmedical.ui.show_approval_details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.show_approvals.model.Approval;

import java.util.List;

public class ApprovalDetailsVM extends ViewModel {
    AppRepository appRepository;

    public ApprovalDetailsVM(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public LiveData<Approval> getApprovalDetails(String id){
        return  appRepository.getApprovalDetails(id);
    }

    public MutableLiveData<List<String>> getImagesNames(String id) {
        return appRepository.getImagesNames(id);
    }
}
