package com.banquemisr.www.bmmedical.ui.Requests;

import com.banquemisr.www.bmmedical.data.AppRepository;

public class RequestViewModel {
    AppRepository appRepository;

    public RequestViewModel(AppRepository appRepository) {
        this.appRepository = appRepository;
        appRepository.getRandomEntities();
    }


}
