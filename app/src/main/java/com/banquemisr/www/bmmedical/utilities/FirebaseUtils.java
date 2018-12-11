package com.banquemisr.www.bmmedical.utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    public static FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    public static DatabaseReference provideUsersReference(){
        return FirebaseDatabase.getInstance().getReference("users");
    }

    public static DatabaseReference provideUserReference(int Oracle){
        return FirebaseDatabase.getInstance().getReference("users");
    }
}
