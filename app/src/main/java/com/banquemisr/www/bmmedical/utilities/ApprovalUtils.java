package com.banquemisr.www.bmmedical.utilities;

import android.content.Context;
import android.util.Log;

import com.banquemisr.www.bmmedical.R;

import java.security.PublicKey;

public class ApprovalUtils {
    public static final String MEDICAL_ANALYSIS_APPROVAL = "medical_analysis_approval";
    public static final String MEDICAL_RAYS_APPROVAL = "Medical_rays_approval";
    public static final String PHYSICAL_THERAPY_APPROVAL = "physical_therapy_approval";
    public static final String DENTAL_APPROVAL = "dental_approval";
    public static final String HOSPITALIZATION_NON_SURGICAL_APPROVAL = "hospitalization_non_surgical_approval";
    public static final String HOSPITALIZATION_SURGICAL_APPROVAL = "hospitalization_surgical_approval";
    public static final String CHEMOTHERAPY_APPROVAL = "chemotherapy_approval";
    public static final String OTHER_APPROVAL = "other_approval";

    public static String getMedicalTypeByResource(String resource, Context context){
        if(resource.equals(context.getResources().getString(R.string.medical_analysis_title))){
            return MEDICAL_ANALYSIS_APPROVAL;
        }else if(resource.equals(context.getResources().getString(R.string.medical_rays_title))){
            return MEDICAL_RAYS_APPROVAL;
        }else if(resource.equals(context.getResources().getString(R.string.physical_therapy_title))){
            return PHYSICAL_THERAPY_APPROVAL;
        }else if(resource.equals(context.getResources().getString(R.string.dental_approval_title))){
            return DENTAL_APPROVAL;
        }else if(resource.equals(context.getResources().getString(R.string.hospitalization_non_title))){
            return HOSPITALIZATION_NON_SURGICAL_APPROVAL;
        }else if(resource.equals(context.getResources().getString(R.string.hospitalization_title))){
            return HOSPITALIZATION_SURGICAL_APPROVAL;
        }else if(resource.equals(context.getResources().getString(R.string.chemotherapy_desc))){
            return CHEMOTHERAPY_APPROVAL;
        }else if(resource.equals(context.getResources().getString(R.string.others_approval_title))){
            return OTHER_APPROVAL;
        }
        return null;
    }

    public static String convertMedicalTypeToResource(String type, Context context){
        if(type.equals(MEDICAL_ANALYSIS_APPROVAL)){
            return context.getResources().getString(R.string.medical_analysis_title);
        } else if(type.equals(MEDICAL_RAYS_APPROVAL)){
            return context.getResources().getString(R.string.medical_rays_title);
        } else if(type.equals(PHYSICAL_THERAPY_APPROVAL)){
            return context.getResources().getString(R.string.physical_therapy_title);
        } else if(type.equals(DENTAL_APPROVAL)){
            return context.getResources().getString(R.string.dental_approval_title);
        } else if(type.equals(HOSPITALIZATION_NON_SURGICAL_APPROVAL)){
            return context.getResources().getString(R.string.hospitalization_non_title);
        } else if(type.equals(HOSPITALIZATION_SURGICAL_APPROVAL)){
            return context.getResources().getString(R.string.hospitalization_title);
        } else if(type.equals(CHEMOTHERAPY_APPROVAL)){
            return context.getResources().getString(R.string.chemotherapy_desc);
        } else if(type.equals(OTHER_APPROVAL)){
            return context.getResources().getString(R.string.others_approval_title);
        }

        return null;
    }
}
