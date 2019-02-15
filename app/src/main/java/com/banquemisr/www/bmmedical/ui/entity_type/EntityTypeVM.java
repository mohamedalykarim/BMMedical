package com.banquemisr.www.bmmedical.ui.entity_type;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.RecyclerView;

public class EntityTypeVM extends ViewModel {
    public MutableLiveData<String> region;
    public MutableLiveData<String> entityType;
    public MutableLiveData<String> specialization;


    public EntityTypeVM() {
        this.region = new MutableLiveData<>();
        this.entityType = new MutableLiveData<>();
        this.specialization = new MutableLiveData<>();
    }

    public void setValues(){
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


    public void checkMedicalCenter(){
        entityType.setValue("medical_center");
    }

    public void checkClinics(){
        entityType.setValue("clinic");
    }

    public void checkHospitals(){
        entityType.setValue("hospital");
    }

}
