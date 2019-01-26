package com.banquemisr.www.bmmedical.ui.show_approval_details;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.banquemisr.www.bmmedical.data.AppRepository;

public class ApprovalDetailsFactory extends ViewModelProvider.NewInstanceFactory {
    AppRepository appRepository;

    public ApprovalDetailsFactory(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ApprovalDetailsVM(appRepository);
    }
}
