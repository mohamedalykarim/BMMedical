package com.banquemisr.www.bmmedical.utilities;

import com.banquemisr.www.bmmedical.ui.requests.model.Filter;

public class FilterUtils {
    public static final String FILTER_TYPE_1 = "filter_type_1";
    public static final String FILTER_TYPE_2 = "filter_type_2";
    public static final String FILTER_TYPE_3 = "filter_type_3";

    public  static String initQuery(Filter filter){
        if(filter.isClinic() && !filter.isHospital()){
            return FILTER_TYPE_1;
        }else if(!filter.isClinic() && filter.isHospital()){
            return FILTER_TYPE_2;
        }else if( (filter.isClinic() && filter.isHospital()) || (!filter.isClinic() && !filter.isHospital()) ){
            return FILTER_TYPE_3;
        }
        return "";
    }

    public static String getSpecializationByPosition(int position) {
        if(position == 1){
            return "Eye";
        }else if(position == 2){
            return "Bone";
        }else if(position == 3){
            return "اطفال";
        }
        return "";
    }
}
