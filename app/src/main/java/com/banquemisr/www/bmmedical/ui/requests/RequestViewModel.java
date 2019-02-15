package com.banquemisr.www.bmmedical.ui.requests;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.requests.model.Filter;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.requests.model.Request;

public class RequestViewModel extends ViewModel {
    MutableLiveData<Filter> filter;
    private DataSource.Factory<Integer,MedicalEntity> dataSourceFactory;
    public Request request;
    private AppRepository appRepository;



    private LiveData<PagedList<MedicalEntity>> medicalEntities;



    public RequestViewModel(AppRepository appRepository) {
        request = new Request();
        this.appRepository = appRepository;
        appRepository.initializeEntitiesData();
        filter = new MutableLiveData<>();
        filter.setValue(new Filter());
    }



    public LiveData<PagedList<MedicalEntity>> getMedicalEntities(LifecycleOwner lifecycleOwner) {
        dataSourceFactory = appRepository.getRandomEntities();
        if(null != medicalEntities){
            medicalEntities.removeObservers(lifecycleOwner);
        }
        medicalEntities = new LivePagedListBuilder<>(dataSourceFactory,3)
                .build();
        return medicalEntities;
    }

    public LiveData<PagedList<MedicalEntity>> getFilteredMedicalEntities(LifecycleOwner lifecycleOwner, String region, String entityType, String specialization) {
        dataSourceFactory = appRepository.getFilteredRandomEntities(region, entityType, specialization);
        if(null != medicalEntities){
            medicalEntities.removeObservers(lifecycleOwner);
        }
        medicalEntities = new LivePagedListBuilder<>(dataSourceFactory,3)
                .build();
        return medicalEntities;
    }


    public LiveData<PagedList<MedicalEntity>> getMedicalEntitiesBySearch(String newText, LifecycleOwner lifecycleOwner) {
        dataSourceFactory = appRepository.getEntitiesBySearch(newText);
        medicalEntities.removeObservers(lifecycleOwner);
        medicalEntities = new LivePagedListBuilder<>(dataSourceFactory,3)
                .build();
        return medicalEntities;

    }

    public LiveData<PagedList<MedicalEntity>> getMedicalEntitiesByFilter(String filterType, Filter filter) {
        dataSourceFactory = appRepository.getEntitiesByFilter(filterType, filter);
        medicalEntities = new LivePagedListBuilder<>(dataSourceFactory,3)
                .build();
        return medicalEntities;
    }
}
