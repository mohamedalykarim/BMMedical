package com.banquemisr.www.bmmedical.utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtils {

    public static FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    public static DatabaseReference provideUsersReference(){
        return FirebaseDatabase.getInstance().getReference("users");
    }

    public static DatabaseReference provideUserReference(String oracle){
        return FirebaseDatabase.getInstance().getReference("users").child(oracle);
    }

    public static DatabaseReference provideEntitiesReference(){
        return FirebaseDatabase.getInstance().getReference("contractors");
    }

    public static DatabaseReference provideRequestsReference(){
        return FirebaseDatabase.getInstance().getReference("requests");
    }

    public static DatabaseReference provideApprovalsReference(){
        return FirebaseDatabase.getInstance().getReference("approvals");
    }

    public static StorageReference provideImageStorageReference(String oracle){
        return FirebaseStorage.getInstance().getReference().child("images").child(oracle);
    }

    public static DatabaseReference getAttachedImagesForApprovalReference() {
        return FirebaseDatabase.getInstance().getReference("approvalAttachedImages");
    }

    public static StorageReference getAttachedImagesForApprovalStorageReference(String oracle) {
        return FirebaseStorage.getInstance().getReference().child("images").child(oracle).child("approvals");
    }
}
