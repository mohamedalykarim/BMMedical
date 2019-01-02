package com.banquemisr.www.bmmedical.ui.Splash;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.ui.Informations.InformationsActivty;
import com.banquemisr.www.bmmedical.ui.MainScreen.MainScreenActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.transaction.TransactionDetailsActivity;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class SplashActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        /**
         *  Login View Model
         */

        LoginViewModelFactory factory = InjectorUtils.provideLoginViewModelFactory(this);
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);

        getUserDetails();

        InjectorUtils.provideAppExecuter().diskIO().execute(()->{
            try {
                Thread.sleep(4000);
                startActivity(new Intent(this,MainScreenActivity.class));
                finish();
            } catch (InterruptedException e) {

            }
        });
    }











    void getUserDetails(){
        loginViewModel.getUser().observe(this, newUser->{
            if(null != newUser){
                SplashVMFactory splashVMFactory = InjectorUtils.provideLSplashVMFactory(this,newUser);
                splashViewModel = ViewModelProviders.of(this,splashVMFactory).get(SplashViewModel.class);
            }

        });
    }
}
