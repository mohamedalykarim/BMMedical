package com.banquemisr.www.bmmedical.ui.show_approvals.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Map;

@Entity
public class Approval implements Parcelable {

    @NonNull
    @PrimaryKey
    String id;
    String status;
    String type;
    Date date;

    @Ignore
    public Approval() {
    }

    public Approval(String id, String status, String type, Date date) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.date = date;
    }

    @Ignore
    protected Approval(Parcel in) {
        id = in.readString();
        status = in.readString();
        type = in.readString();
        date = (Date) in.readSerializable();

    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(status);
        dest.writeString(type);
        dest.writeSerializable(date);
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    public static final Creator<Approval> CREATOR = new Creator<Approval>() {
        @Override
        public Approval createFromParcel(Parcel in) {
            return new Approval(in);
        }

        @Override
        public Approval[] newArray(int size) {
            return new Approval[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}