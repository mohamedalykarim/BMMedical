package com.banquemisr.www.bmmedical.ui.requests;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.banquemisr.www.bmmedical.data.AppRepository;

public class RequestViewModelFactory extends  ViewModelProvider.NewInstanceFactory {
    AppRepository appRepository;

    public RequestViewModelFactory(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RequestViewModel(appRepository);
    }
}
