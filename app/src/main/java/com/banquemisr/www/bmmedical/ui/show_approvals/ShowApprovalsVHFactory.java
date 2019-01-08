package com.banquemisr.www.bmmedical.ui.show_approvals;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.banquemisr.www.bmmedical.data.AppRepository;

public class ShowApprovalsVHFactory extends ViewModelProvider.NewInstanceFactory{
    AppRepository appRepository;
    String oracle;

    public ShowApprovalsVHFactory(AppRepository appRepository, String oracle) {
        this.appRepository = appRepository;
        this.oracle = oracle;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ShowApprovalsVH(appRepository, oracle);
    }
}
