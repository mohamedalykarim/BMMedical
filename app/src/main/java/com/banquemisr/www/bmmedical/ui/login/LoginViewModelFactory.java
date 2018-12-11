package com.banquemisr.www.bmmedical.ui.login;


import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.banquemisr.www.bmmedical.data.AppRepository;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    AppRepository appRepository;

    public LoginViewModelFactory(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(appRepository);
    }
}
