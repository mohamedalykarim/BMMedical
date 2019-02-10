package com.banquemisr.www.bmmedical.ui.entity_type;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class EntityTypeVmFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EntityTypeVM();
    }
}
