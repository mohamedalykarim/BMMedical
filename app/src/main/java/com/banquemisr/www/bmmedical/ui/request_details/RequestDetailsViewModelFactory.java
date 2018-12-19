package com.banquemisr.www.bmmedical.ui.request_details;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.banquemisr.www.bmmedical.data.AppRepository;

public class RequestDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    AppRepository appRepository;
    String id;

    public RequestDetailsViewModelFactory(AppRepository appRepository, String id) {
        this.appRepository = appRepository;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RequestDetailsViewModel(appRepository,id);
    }
}
