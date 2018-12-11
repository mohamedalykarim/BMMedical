package com.banquemisr.www.bmmedical.data;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.banquemisr.www.bmmedical.data.services.UserSyncIntentService;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class NetworkDataHelper {
    private static final String ORACLE = "oracle";
    private static final String FETCH_USER_DATA = "get_user_data";
    Context mContext;
    private static final Object LOCK = new Object();
    private static NetworkDataHelper sInstance;
    MutableLiveData<User> user;


    private NetworkDataHelper(Context context) {
        mContext = context;
        user = new MutableLiveData<>();

    }

    /**
     * Get the singleton for this class
     */
    public static NetworkDataHelper getInstance(Context context) {

        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NetworkDataHelper(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void startFetchUserService(int oracle) {
        Intent intentToFetch = new Intent(mContext, UserSyncIntentService.class);
        intentToFetch.putExtra(ORACLE, oracle);
        intentToFetch.setAction(FETCH_USER_DATA);
        mContext.startService(intentToFetch);
    }

    public void fetchUser(int oracle){

        FirebaseUtils.provideUserReference(oracle).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    User retrievedUser = childSnapshot.getValue(User.class);
                    user.setValue(retrievedUser);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                fetchUser(oracle);
            }
        });
    }

}
