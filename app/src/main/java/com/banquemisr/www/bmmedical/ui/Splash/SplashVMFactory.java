package com.banquemisr.www.bmmedical.ui.Splash;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.login.model.User;

public class SplashVMFactory extends ViewModelProvider.NewInstanceFactory {
    AppRepository appRepository;
    User user;

    public SplashVMFactory(AppRepository appRepository, User user) {
        this.appRepository = appRepository;
        this.user = user;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SplashViewModel(appRepository, user);
    }
}
