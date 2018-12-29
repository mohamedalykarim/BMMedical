package com.banquemisr.www.bmmedical.data.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {
    @TypeConverter
    public static Date toDate(Long value) {
        if(null == value){
         return null;
        }else {
            Date date = new Date(value);
            return date;
        }
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
