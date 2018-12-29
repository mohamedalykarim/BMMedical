package com.banquemisr.www.bmmedical.ui.request_details.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class RequestDetails implements Parcelable {
    @PrimaryKey
    @NonNull
    String id;
    Date date;
    String contractorId;
    int oracle;
    String status;
    String name;
    String benfituaryName;
    String specialization;




    @Ignore
    public RequestDetails() {
    }

    public RequestDetails(@NonNull String id, Date date, String contractorId, int oracle, String status, String name, String benfituaryName, String specialization) {
        this.id = id;
        this.date = date;
        this.contractorId = contractorId;
        this.oracle = oracle;
        this.status = status;
        this.name = name;
        this.benfituaryName = benfituaryName;
        this.specialization = specialization;
    }


    @Ignore
    protected RequestDetails(Parcel in) {
        id = in.readString();
        date = (Date) in.readSerializable();
        contractorId = in.readString();
        oracle = in.readInt();
        status = in.readString();
        name = in.readString();
        benfituaryName = in.readString();
        specialization = in.readString();
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeSerializable(date);
        dest.writeString(contractorId);
        dest.writeInt(oracle);
        dest.writeString(status);
        dest.writeString(name);
        dest.writeString(benfituaryName);
        dest.writeString(specialization);
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    public static final Creator<RequestDetails> CREATOR = new Creator<RequestDetails>() {
        @Override
        public RequestDetails createFromParcel(Parcel in) {
            return new RequestDetails(in);
        }

        @Override
        public RequestDetails[] newArray(int size) {
            return new RequestDetails[size];
        }
    };

    @NonNull
    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getContractorId() {
        return contractorId;
    }

    public int getOracle() {
        return oracle;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getBenfituaryName() {
        return benfituaryName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public void setOracle(int oracle) {
        this.oracle = oracle;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBenfituaryName(String benfituaryName) {
        this.benfituaryName = benfituaryName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
