package com.banquemisr.www.bmmedical.ui.requests.model;

public class Filter {
   boolean clinic = false;
   boolean hospital = false;
   int specialization = 0;

    public boolean isClinic() {
        return clinic;
    }

    public boolean isHospital() {
        return hospital;
    }

    public Integer getSpecialization() {
        return specialization;
    }

    public void setClinic(boolean clinic) {
        this.clinic = clinic;
    }

    public void setHospital(boolean hospital) {
        this.hospital = hospital;
    }

    public void setSpecialization(int specialization) {
        this.specialization = specialization;
    }
}
