package com.banquemisr.www.bmmedical.ui.show_approvals;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.show_approvals.model.Approval;
import com.banquemisr.www.bmmedical.utilities.ApprovalUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowApprovalsVH extends ViewModel {
    AppRepository appRepository;
    MutableLiveData<ArrayList<Uri>> uris;
    LiveData<List<Approval>> approvals;
    String oracle;

    MutableLiveData<Boolean> startAttachEvent, startAddRequstEvent;

    public ShowApprovalsVH(AppRepository appRepository, String oracle) {
        this.appRepository = appRepository;
        startAttachEvent = new MutableLiveData<>();
        startAddRequstEvent = new MutableLiveData<>();
        uris = new MutableLiveData<>();
        this.oracle = oracle;
    }


    public void startAddApprovalRequest(String type, Context context){
        if(uris.getValue().size()>0){
            Approval approval = new Approval();
            approval.setDate(new Date());
            approval.setStatus("sent to medical");
            String englishType = ApprovalUtils.getMedicalTypeByResource(type, context);
            approval.setType(englishType);
            appRepository.startAddTheApprovalRequest(approval,uris.getValue(),oracle);
        }
    }


    /**
     * Handling Uris of the selected images
     */

    public MutableLiveData<ArrayList<Uri>> getUris() {
        return uris;
    }

    public void setUris(ArrayList<Uri> uris) {
        this.uris.setValue(uris);
    }

    public void postUris(ArrayList<Uri> uris) {
        this.uris.postValue(uris);
    }


    /**
     * Handling Events
     * @return
     */

    public MutableLiveData<Boolean> getStartAttachEvent() {
        return startAttachEvent;
    }

    public MutableLiveData<Boolean> getStartAddRequstEvent() {
        return startAddRequstEvent;
    }


    /**
     * Handling layout methods
     */

    public void startAttach(){
        startAttachEvent.setValue(true);
    }

    public void addTheApprovalRequest(){
        startAddRequstEvent.setValue(true);
    }


    /**
     * Handling approvals
     */

    public LiveData<List<Approval>> getApprovals(String type) {
        return appRepository.getApprovalsRequests(oracle, type);
    }

}
