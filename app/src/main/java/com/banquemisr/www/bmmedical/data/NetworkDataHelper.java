package com.banquemisr.www.bmmedical.data;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.banquemisr.www.bmmedical.data.services.UserSyncIntentService;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NetworkDataHelper {
    private static final String FETCH_USER_DATA = "get_user_data";
    private static final String FETCH_ENTITIES_DATA = "get_entities_data";

    Context mContext;
    private static final Object LOCK = new Object();
    private static NetworkDataHelper sInstance;
    MutableLiveData<User> user;
    MutableLiveData<List<MedicalEntity>> entities;


    private NetworkDataHelper(Context context) {
        mContext = context;
        user = new MutableLiveData<>();
        entities = new MutableLiveData<>();

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

    public void startFetchUserService() {
        Intent intentToFetch = new Intent(mContext, UserSyncIntentService.class);
        intentToFetch.setAction(FETCH_USER_DATA);
        mContext.startService(intentToFetch);
    }

    public void startFetchEntitiesService() {
        Intent intentToFetch = new Intent(mContext, UserSyncIntentService.class);
        intentToFetch.setAction(FETCH_ENTITIES_DATA);
        mContext.startService(intentToFetch);
    }

    public void fetchUsers(){

        FirebaseUtils.provideUsersReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    try{
                        User retrievedUser = childSnapshot.getValue(User.class);
                        user.setValue(retrievedUser);
                    }catch (Exception e){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                fetchUsers();
            }
        });
    }

    public void fetchEntities() {
        Query query = FirebaseUtils.provideEntitiesReference().orderByKey();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<MedicalEntity> newEntities = new ArrayList<>();

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        try{
                            MedicalEntity medicalEntity = childSnapshot.getValue(MedicalEntity.class);
                            newEntities.add(medicalEntity);
                        }catch (Exception e){

                        }
                }
                entities.setValue(newEntities);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
