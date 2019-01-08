package com.banquemisr.www.bmmedical.utilities;

import android.content.Context;
import android.util.Log;

import com.banquemisr.www.bmmedical.R;

import java.security.PublicKey;

public class ApprovalUtils {
    private static final String MEDICAL_ANALYSIS_APPROVAL = "Medical Analysis Approval";

    public static String getMedicalTypeByResource(String resource, Context context){
        if(resource.equals(context.getResources().getString(R.string.medical_analysis_title))){
            return MEDICAL_ANALYSIS_APPROVAL;
        }


        return null;
    }
}
