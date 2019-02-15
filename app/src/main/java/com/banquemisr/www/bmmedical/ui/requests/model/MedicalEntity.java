package com.banquemisr.www.bmmedical.ui.requests.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "entities")
public class MedicalEntity {

    @NonNull
    @PrimaryKey
    String id;
    String name;
    String type;
    String region;
    String phone;
    String address;
    String price;
    String doctor;
    String specialist;
    String branch;
    double longitude;
    double latitude ;

    @Ignore
    public MedicalEntity() {
    }

    public MedicalEntity(@NonNull String id, String name, String type, String region, String phone,
                         String address, String price, String doctor, String specialist, String branch,
                         double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.region = region;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.doctor = doctor;
        this.specialist = specialist;
        this.branch = branch;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRegion() {
        return region;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getSpecialist() {
        return specialist;
    }

    public String getBranch() {
        return branch;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
