package com.banquemisr.www.bmmedical.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.banquemisr.www.bmmedical.data.AppRepository;
import com.banquemisr.www.bmmedical.ui.login.model.Login;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class LoginViewModel extends ViewModel{
    public Login login;
    private FirebaseAuth mAuth;
    LiveData<User> user;
    LiveData<List<RequestDetails>> request;

    AppRepository appRepository;


    public LoginViewModel(AppRepository appRepository) {
        login = new Login();
        mAuth = FirebaseUtils.provideFirebaseAuth();
        this.appRepository = appRepository;
        user = appRepository.getUser();
    }

    public void checkIfValid(Login login){
        if (login.getOracle().length() > 3 && login.getPassword().length() > 3){
            login.setValid(true);
        }else {
            login.setValid(false);
        }
    }

    public  void onClickLoginButton(){
        if(null == login.getOracle()){
            return;
        }else if(null == login.getPassword()){
            return;
        }

        checkIfValid(login);
        if (login.isValid()){
            login.setLoginEvent(true);
            String email = login.getOracle() + "@banquemisr.com";
            signIn(email, login.getPassword());
        }
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            login.setLogged(true);
                            login.setLoginEvent(false);
                            int oracle = Integer.valueOf(email.replace("@banquemisr.com",""));
                            user = appRepository.getUserByOracle(oracle);

                        }else {
                            login.setLogged(false);
                            login.setLoginEvent(false);
                        }
                    }
                });

    }

    public void onClickLogin(){
        login.setLoginPressedEvent(true);
    }

    public void onClickLogout(){
        FirebaseUtils.provideFirebaseAuth().signOut();
        login.setLogged(false);
        login.setLoginPressedEvent(true);
        appRepository.deleteALLUsers();

    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(LiveData<User> user) {
        this.user = user;
    }

    public LiveData<List<RequestDetails>> getRequest(String oracle) {
        request = appRepository.getRequests(oracle);
        return request;
    }

    public LiveData<MedicalEntity> getEntityById(String contractorId) {
        return appRepository.getEntityById(contractorId);
    }
}
