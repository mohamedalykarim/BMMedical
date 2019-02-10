package com.banquemisr.www.bmmedical.ui.entity_type;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.RecyclerView;

public class EntityTypeVM extends ViewModel {
    MutableLiveData<String> region;
    MutableLiveData<String> entityType;
    MutableLiveData<String> specialization;


    public EntityTypeVM() {
        this.region = new MutableLiveData<>();
        this.entityType = new MutableLiveData<>();
        this.specialization = new MutableLiveData<>();

        this.region.setValue("");
        this.entityType.setValue("");
        this.specialization.setValue("");
    }

    public void checkCairoRegions(){
        region.setValue("cairo_regions");
    }

    public void checkOtherRegions(){
        region.setValue("other_regions");
    }
}
