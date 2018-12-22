package com.banquemisr.www.bmmedical.ui.MainScreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.banquemisr.www.bmmedical.Adapters.MenuAdapter;
import com.banquemisr.www.bmmedical.Adapters.RequestsAdapter;
import com.banquemisr.www.bmmedical.data.model.MenuItem;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityMainScreenBinding;
import com.banquemisr.www.bmmedical.databinding.NavMainBinding;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.ui.login.model.User;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity {
    private static final int LOGIN_REQUEST = 1;

    LoginViewModel loginViewModel;
    ActivityMainScreenBinding binding;

    RecyclerView mainMenuRecyclerView;
    MenuAdapter mainMenuAdpater;
    LinearLayoutManager mainMenuLayoutManager;
    List<MenuItem> mainMenuListItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_screen);
        binding.setLifecycleOwner(this);

        mainMenuListItems = new ArrayList<>();

        mainMenuLayoutManager = new LinearLayoutManager(this);
        mainMenuRecyclerView = binding.mainMenuRecycler;
        mainMenuAdpater = new MenuAdapter(this);

        mainMenuRecyclerView.setLayoutManager(mainMenuLayoutManager);
        mainMenuAdpater.setMenuItems(mainMenuListItems);
        mainMenuAdpater.setAdapterTypeMainMenu();

        mainMenuRecyclerView.setAdapter(mainMenuAdpater);
        mainMenuRecyclerView.setHasFixedSize(true);


        mainMenuListItems.add(new MenuItem(getString(R.string.informations), getString(R.string.information_desc),R.drawable.information));
        mainMenuListItems.add(new MenuItem(getString(R.string.medical_request), getString(R.string.medical_request_desc), R.drawable.request));
        mainMenuListItems.add(new MenuItem(getString(R.string.approval), getString(R.string.approval_desc), R.drawable.approval));
        mainMenuAdpater.notifyDataSetChanged();


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
                    startActivityForResult(new Intent(MainScreenActivity.this, LoginActivity.class), LOGIN_REQUEST);
                }
            }
        });


    }

    void requestListView(List<RequestDetails> requestDetails){
        NavMainBinding navMainBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.nav_main,null,false);
        ListView listView = findViewById(R.id.request_list_view);
        RequestsAdapter requestsAdapter = new RequestsAdapter(
                this,
                requestDetails
        );
        listView.setAdapter(requestsAdapter);
    }

    void getUserDetails(){
            loginViewModel.getUser().observe(this, newUser->{
                binding.setUser(newUser);

                // Requests Details of the current user
                if(null != newUser){
                    loginViewModel.getRequest(newUser.getOracle()+"")
                            .observe(this, this::requestListView);
                }



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
