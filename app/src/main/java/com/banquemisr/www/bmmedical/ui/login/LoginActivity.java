package com.banquemisr.www.bmmedical.ui.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityLoginBinding;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);

        LoginViewModelFactory factory = InjectorUtils.provideLoginViewModelFactory(this);
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);

        loginViewModel.login.setLoginPressedEvent(false);
        loginViewModel.login.isLogged().observe(this, new Observer <Boolean>( ) {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean) finish();
            }
        });


        setResult(RESULT_OK);

    }

}
