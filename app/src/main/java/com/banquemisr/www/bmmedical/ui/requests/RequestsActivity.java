package com.banquemisr.www.bmmedical.ui.requests;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.banquemisr.www.bmmedical.Adapters.EntityAdapter;
import com.banquemisr.www.bmmedical.Adapters.RequestsAdapter;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityRequestsBinding;
import com.banquemisr.www.bmmedical.databinding.NavMainBinding;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.List;

public class RequestsActivity extends AppCompatActivity {
    private static final int LOGIN_REQUEST = 1;
    ActivityRequestsBinding binding;
    RequestViewModel requestViewModel;
    LoginViewModel loginViewModel;

    EntityAdapter entityRecyclerAdapter;
    RecyclerView entityRecyclerView;
    LinearLayoutManager entityLayoutManager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_requests);
        binding.setLifecycleOwner(this);

        drawerLayout = binding.drawer;
        navigationView = binding.navView;

        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.da));
        setSupportActionBar(toolbar);





        entityRecyclerAdapter = new EntityAdapter(this);
        entityRecyclerView = binding.entityRecyclerView;
        entityLayoutManager = new LinearLayoutManager(this);

        entityRecyclerView.setLayoutManager(entityLayoutManager);
        entityRecyclerView.setAdapter(entityRecyclerAdapter);


        /**
         * RequestDetails View Model
         */

        RequestViewModelFactory requestViewModelFactory = InjectorUtils
                .provideRequestViewModelFactory(this);

        requestViewModel = ViewModelProviders
                .of(this,requestViewModelFactory)
                .get(RequestViewModel.class);

        binding.setRequestViewModel(requestViewModel);


        /**
         *  Login View Model
         */

        LoginViewModelFactory factory = InjectorUtils.provideLoginViewModelFactory(this);
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);

        getOutifNotLogin();
        getUserDetails();
        startListeningToMedicalEntities();
        startListeningtoSearch();

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

            // Requests Details of the current user
            if(null != newUser){
                loginViewModel.getRequest(newUser.getOracle()+"")
                        .observe(this, requests->{

                            for (RequestDetails requestDetails: requests){
                                View view = getLayoutInflater().inflate(R.layout.row_request_list_item,null,false);
                                binding.navMain.requestsList.addView(view);
                            }
                        });
            }



        });
    }

    void startListeningToMedicalEntities(){
        requestViewModel.getMedicalEntities(this).observe(this,pagedListEntity->{
            entityRecyclerAdapter.submitList(pagedListEntity);
        });
    }

    void startListeningtoSearch(){
        requestViewModel.request.getSearchText().observe(this, searchText->{
            if(searchText.equals("")){
                requestViewModel.getMedicalEntities(this).observe(this,pagedListEntity->{
                    entityRecyclerAdapter = null;
                    entityRecyclerAdapter = new EntityAdapter(this);
                    entityRecyclerView.setAdapter(entityRecyclerAdapter);
                    entityRecyclerAdapter.submitList(pagedListEntity);
                });
            }else{
                requestViewModel.getMedicalEntitiesBySearch(searchText,this).observe(this,pagedListEntity->{
                    entityRecyclerAdapter = null;
                    entityRecyclerAdapter = new EntityAdapter(this);
                    entityRecyclerView.setAdapter(entityRecyclerAdapter);
                    entityRecyclerAdapter.submitList(pagedListEntity);
                });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.request_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.request_menu_filter:

                break;


        }

        return super.onOptionsItemSelected(item);
    }



}
