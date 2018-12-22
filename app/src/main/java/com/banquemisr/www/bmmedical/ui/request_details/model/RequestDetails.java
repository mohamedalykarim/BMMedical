package com.banquemisr.www.bmmedical.ui.request_details.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class RequestDetails implements Parcelable {
    @PrimaryKey
    @NonNull
    String id;
    long date;
    String contractorId;
    int userOracle;
    String status;
    String name;


    @Ignore
    public RequestDetails() {
    }

    public RequestDetails(String id, long date, String contractorId, int userOracle) {
        this.id = id;
        this.date = date;
        this.contractorId = contractorId;
        this.userOracle = userOracle;
    }


    @Ignore
    protected RequestDetails(Parcel in) {
        id = in.readString();
        date = in.readLong();
        contractorId = in.readString();
        userOracle = in.readInt();
        status = in.readString();
        name = in.readString();
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

    public String getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getContractorId() {
        return contractorId;
    }

    public int getUserOracle() {
        return userOracle;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public void setUserOracle(int userOracle) {
        this.userOracle = userOracle;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(date);
        dest.writeString(contractorId);
        dest.writeInt(userOracle);
        dest.writeString(status);
        dest.writeString(name);
    }
}
