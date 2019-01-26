package com.banquemisr.www.bmmedical.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.util.Log;

import com.banquemisr.www.bmmedical.data.services.UserSyncIntentService;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.ui.requests.model.Request;
import com.banquemisr.www.bmmedical.ui.show_approvals.model.Approval;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NetworkDataHelper {
    private static final String FETCH_USER_DATA = "get_user_data";
    private static final String FETCH_ENTITIES_DATA = "get_entities_data";
    private static final String ADD_REQUEST_EVENT = "add_request_event";
    private static final String FETCH_REQUEST_DETAILS_DATA = "get_request_details_data";
    private static final String ADD_APPROVAL_REQUEST = "add_approval_request";
    private static final String ADD_APPROVAL_REQUEST_URIS = "add_approval_request_uris";
    private static final String ADD_APPROVAL_REQUEST_ORACLE = "add_approval_request_oracle";
    private static final String FETCH_APPROVAL_REQUESTS = "get_approval_request";

    Context mContext;
    private static final Object LOCK = new Object();
    private static NetworkDataHelper sInstance;

    MutableLiveData<User> user;
    MutableLiveData<List<MedicalEntity>> entities;
    MutableLiveData<List<RequestDetails>> requests;
    MutableLiveData<List<Approval>> approvalsRequests;


    private NetworkDataHelper(Context context) {
        mContext = context;
        user = new MutableLiveData<>();
        entities = new MutableLiveData<>();
        requests = new MutableLiveData<>();
        approvalsRequests = new MutableLiveData<>();

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

    public void startAddTheMedicalRequest(RequestDetails requestDetails) {
        Intent intent = new Intent(mContext, UserSyncIntentService.class);
        intent.setAction(ADD_REQUEST_EVENT);
        intent.putExtra(ADD_REQUEST_EVENT, requestDetails);
        mContext.startService(intent);
    }

    public void startaddingTheApprovalRequest(Approval approval, ArrayList<Uri> uris, String oracle){
        Intent intent = new Intent(mContext, UserSyncIntentService.class);
        intent.setAction(ADD_APPROVAL_REQUEST);
        intent.putExtra(ADD_APPROVAL_REQUEST, approval);
        intent.putExtra(ADD_APPROVAL_REQUEST_URIS, uris);
        intent.putExtra(ADD_APPROVAL_REQUEST_ORACLE, oracle);
        mContext.startService(intent);
    }

    public void startFetchApprovalRequestsData(String oracle){
        Intent intent = new Intent(mContext, UserSyncIntentService.class);
        intent.setAction(FETCH_APPROVAL_REQUESTS);
        intent.putExtra(FETCH_APPROVAL_REQUESTS, oracle);
        mContext.startService(intent);

    }

    public void startFetchRequestDetailsService(String oracle) {
        Intent intentToFetch = new Intent(mContext, UserSyncIntentService.class);
        intentToFetch.setAction(FETCH_REQUEST_DETAILS_DATA);
        intentToFetch.putExtra(FETCH_REQUEST_DETAILS_DATA, oracle);
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

    public void fetchApprovalRequests(String oracle){
        DatabaseReference reference = FirebaseUtils.provideApprovalsReference().child(oracle);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Approval> approvals = new ArrayList<>();

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    try{
                        Approval approval = childSnapshot.getValue(Approval.class);
                        approvals.add(approval);
                    }catch (Exception e){

                    }
                }
                approvalsRequests.setValue(approvals);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void fetchRequestData(String oracle) {
        FirebaseUtils.provideRequestsReference()
                .child(oracle)
                .orderByChild("status")
                .equalTo("approved")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<RequestDetails> requestDetails = new ArrayList<>();

                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            try {
                                requestDetails.add(child.getValue(RequestDetails.class));
                            }catch (Exception e){

                            }
                        }
                        requests.setValue(requestDetails);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void addTheMedicalRequest(RequestDetails requestDetails){
        DatabaseReference requestReference = FirebaseUtils.provideRequestsReference().push();
        String id = requestReference.getKey();
        requestDetails.setId(id);

        FirebaseUtils.provideRequestsReference()
                .child(requestDetails.getOracle()+"")
                .orderByChild("specialization")
                .equalTo(requestDetails.getSpecialization())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getChildrenCount() == 0){

                            FirebaseUtils.provideRequestsReference().child(requestDetails.getOracle()+"")
                                    .child(requestDetails.getId())
                                    .setValue(requestDetails);


                        }else{
                            boolean isDateOk = true;
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date());
                            calendar.add(Calendar.DATE, -15);
                            long dateBefore15 = calendar.getTimeInMillis();

                            for (DataSnapshot child : dataSnapshot.getChildren()){
                                long date = child.getValue(RequestDetails.class).getDate().getTime();
                                if(date > dateBefore15){
                                    isDateOk = false;
                                }
                            }

                            if(isDateOk){
                                FirebaseUtils.provideRequestsReference().child(requestDetails.getOracle()+"")
                                        .child(requestDetails.getId())
                                        .setValue(requestDetails);
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });






    }


    public void addTheApprovalRequest(Approval approval, ArrayList<Uri> uris, String oracle){

        DatabaseReference reference = FirebaseUtils.provideApprovalsReference().child(oracle).push();
        String id = reference.getKey();
        approval.setId(id);

        reference.setValue(approval)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        for (Uri uri : uris){
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),uri);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                                byte[] data = baos.toByteArray();


                                // Add the file to storage
                                StorageReference reference = FirebaseUtils.provideImageStorageReference(oracle).child("approvals").child(id);
                                StorageReference file = reference.child(oracle + getFileName(uri));
                                file.putBytes(data);

                            } catch (IOException e) {

                            }

                        }

                    }
                });



    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }



}