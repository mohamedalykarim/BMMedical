package com.banquemisr.www.bmmedical.ui.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.requests.model.Request;

public class RequestViewModel extends ViewModel {
    public Request request;
    private AppRepository appRepository;
    String searchText ="";


    private MediatorLiveData<PagedList<MedicalEntity>> entityMediatorLiveData;

    private LiveData<PagedList<MedicalEntity>> medicalEntities;
    private LiveData<PagedList<MedicalEntity>> medicalEntitiesBySearch;

    public RequestViewModel(AppRepository appRepository) {
        entityMediatorLiveData = new MediatorLiveData<>();
        medicalEntities = new LivePagedListBuilder<Integer,MedicalEntity>
                (appRepository.getRandomEntities(),
                        3)
                .build();

        entityMediatorLiveData.addSource(medicalEntities,value->entityMediatorLiveData.setValue(value));
        entityMediatorLiveData.removeSource(medicalEntitiesBySearch);


        medicalEntitiesBySearch = new LivePagedListBuilder<Integer,MedicalEntity>
                (appRepository.getEntitiesBySearch(searchText),
                        3)
                .build();
        request = new Request();
    }



    public void getEntitiesBySearch(String searchText){
        this.searchText = searchText;

        if(null != searchText || !searchText.equals("")){
            entityMediatorLiveData.addSource(medicalEntitiesBySearch,value->entityMediatorLiveData.setValue(value));
            entityMediatorLiveData.removeSource(medicalEntities);
        }else{
            entityMediatorLiveData.addSource(medicalEntities,value->entityMediatorLiveData.setValue(value));
            entityMediatorLiveData.removeSource(medicalEntitiesBySearch);
        }
    }

    public MediatorLiveData<PagedList<MedicalEntity>> getEntityMediatorLiveData() {
        return entityMediatorLiveData;
    }
}
