package com.banquemisr.www.bmmedical.ui.Requests;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banquemisr.www.bmmedical.Adapters.EntityRecyclerAdapter;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityRequestsBinding;
import com.banquemisr.www.bmmedical.ui.MainScreen.MainScreenActivity;
import com.banquemisr.www.bmmedical.ui.Requests.Model.MedicalEntity;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.ArrayList;

public class RequestsActivity extends AppCompatActivity {
    private static final int LOGIN_REQUEST = 1;
    ActivityRequestsBinding binding;
    ArrayList<MedicalEntity> entities;
    LoginViewModel loginViewModel;

    EntityRecyclerAdapter entityRecyclerAdapter;
    RecyclerView entityRecyclerView;
    LinearLayoutManager entityLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_requests);
        binding.setLifecycleOwner(this);

        entities = new ArrayList<>();
        entityRecyclerAdapter = new EntityRecyclerAdapter(this);
        entityRecyclerView = binding.entityRecyclerView;
        entityLayoutManager = new LinearLayoutManager(this);

        entityRecyclerView.setLayoutManager(entityLayoutManager);
        entityRecyclerView.setAdapter(entityRecyclerAdapter);

        entities.add(new MedicalEntity());
        entities.add(new MedicalEntity());
        entities.add(new MedicalEntity());
        entities.add(new MedicalEntity());
        entities.add(new MedicalEntity());
        entities.add(new MedicalEntity());
        entities.add(new MedicalEntity());

        entities.get(0).setType("hospital");
        entities.get(6).setType("hospital");

        entityRecyclerAdapter.setEntities(entities);
        entityRecyclerAdapter.notifyDataSetChanged();



        /**
         *  Login View Model
         */

        LoginViewModelFactory factory = InjectorUtils.provideLoginViewModelFactory(this);
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);

        getOutifNotLogin();
        getUserDetails();

    }


    public void getOutifNotLogin(){
        loginViewModel.login.getLoginPressedEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    startActivityForResult(new Intent(RequestsActivity.this, LoginActivity.class), LOGIN_REQUEST);
                }
            }
        });


    }

    void getUserDetails(){
        loginViewModel.getUser().observe(this, newUser->{
            binding.setUser(newUser);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(null == FirebaseUtils.provideFirebaseAuth().getCurrentUser()){
            loginViewModel.login.setLogged(false);
            loginViewModel.login.setLoginPressedEvent(false);
        }else{
            loginViewModel.login.setLogged(true);
            loginViewModel.login.setLoginPressedEvent(false);
        }
    }
}
