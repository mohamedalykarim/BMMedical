package com.banquemisr.www.bmmedical.data.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.banquemisr.www.bmmedical.data.NetworkDataHelper;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.show_approvals.model.Approval;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.ArrayList;

public class UserSyncIntentService extends IntentService {
    private static final String FETCH_USER_DATA = "get_user_data";
    private static final String FETCH_ENTITIES_DATA = "get_entities_data";
    private static final String ADD_REQUEST_EVENT = "add_request_event";
    private static final String FETCH_REQUEST_DETAILS_DATA = "get_request_details_data";
    private static final String ADD_APPROVAL_REQUEST = "add_approval_request";
    private static final String ADD_APPROVAL_REQUEST_URIS = "add_approval_request_uris";
    private static final String ADD_APPROVAL_REQUEST_ORACLE = "add_approval_request_oracle";
    private static final String FETCH_APPROVAL_REQUESTS = "get_approval_request";



    private static final String ORACLE = "oracle";

    public UserSyncIntentService() {
        super("UserSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NetworkDataHelper networkDataHelper = InjectorUtils.provideNetworkDataHelper(this.getApplicationContext());

        if(intent.getAction().equals(FETCH_USER_DATA)){
            networkDataHelper.fetchUsers();
        }else if(intent.getAction().equals(FETCH_ENTITIES_DATA)){
            networkDataHelper.fetchEntities();
        }
        else if(intent.getAction().equals(ADD_REQUEST_EVENT)){
            if(intent.hasExtra(ADD_REQUEST_EVENT)){
                RequestDetails requestDetails = intent.getParcelableExtra(ADD_REQUEST_EVENT);
                networkDataHelper.addTheMedicalRequest(requestDetails);
            }
        }else if(intent.getAction().equals(FETCH_REQUEST_DETAILS_DATA)){
            if(intent.hasExtra(FETCH_REQUEST_DETAILS_DATA)){
                String oracle = intent.getStringExtra(FETCH_REQUEST_DETAILS_DATA);
                networkDataHelper.fetchRequestData(oracle);
            }

        }else if(intent.getAction().equals(ADD_APPROVAL_REQUEST)){
            if(intent.hasExtra(ADD_APPROVAL_REQUEST)){
                Approval approval = intent.getParcelableExtra(ADD_APPROVAL_REQUEST);
                ArrayList<Uri> uris = intent.getParcelableArrayListExtra(ADD_APPROVAL_REQUEST_URIS);
                String oracle = intent.getStringExtra(ADD_APPROVAL_REQUEST_ORACLE);
                networkDataHelper.addTheApprovalRequest(approval, uris, oracle);
            }
        }else if(intent.getAction().equals(FETCH_APPROVAL_REQUESTS)){
            if(intent.hasExtra(FETCH_APPROVAL_REQUESTS)){
                String oracle = intent.getStringExtra(FETCH_APPROVAL_REQUESTS);
                networkDataHelper.fetchApprovalRequests(oracle);
            }
        }

    }
}
