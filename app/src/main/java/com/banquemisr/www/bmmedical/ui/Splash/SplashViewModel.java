package com.banquemisr.www.bmmedical.ui.Splash;

import android.arch.lifecycle.ViewModel;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class SplashViewModel extends ViewModel {
    AppRepository appRepository;

    public SplashViewModel(AppRepository appRepository, User user) {
        this.appRepository = appRepository;
        InjectorUtils.provideAppExecuter().diskIO().execute(()->{
            appRepository.deleteAllRequests();
            appRepository.initializeRequestsDetails(user.getOracle()+"");
        });

    }
}
