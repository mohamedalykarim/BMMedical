package com.banquemisr.www.bmmedical.data.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.banquemisr.www.bmmedical.data.NetworkDataHelper;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class UserSyncIntentService extends IntentService {
    private static final String FETCH_USER_DATA = "get_user_data";
    private static final String FETCH_ENTITIES_DATA = "get_entities_data";




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

    }
}
