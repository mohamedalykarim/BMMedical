package com.banquemisr.www.bmmedical.ui.login.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    private int oracle;
    private String name;
    private String phone;
    private String address;
    private String branch;
    private String position;
    private long latitude;
    private long longitude;

    @Ignore
    public User() {
    }

    public User(int oracle, String name, String phone, String address, String branch, String position, long latitude, long longitude) {
        this.oracle = oracle;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.branch = branch;
        this.position = position;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getOracle() {
        return oracle;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getBranch() {
        return branch;
    }

    public String getPosition() {
        return position;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setOracle(int oracle) {
        this.oracle = oracle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
