package com.banquemisr.www.bmmedical.ui.Requests.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.Exclude;

@Entity(tableName = "entities")
public class MedicalEntity {
    @PrimaryKey(autoGenerate = true)
    int id;

    String name;
    String type;
    String phone;
    String address;
    String price;
    String doctor;
    String specialist;
    String branch;
    long longitude;
    long latitude ;

    @Ignore
    public MedicalEntity() {
    }

    public MedicalEntity(int id, String name, String type, String phone, String address, String price, String doctor, String specialist, String branch, long longitude, long latitude) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.doctor = doctor;
        this.specialist = specialist;
        this.branch = branch;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Exclude
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
