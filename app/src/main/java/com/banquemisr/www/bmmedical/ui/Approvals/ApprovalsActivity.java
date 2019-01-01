package com.banquemisr.www.bmmedical.ui.Approvals;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.banquemisr.www.bmmedical.Adapters.MenuAdapter;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.data.model.MenuItem;
import com.banquemisr.www.bmmedical.databinding.ActivityApprovalsBinding;
import com.banquemisr.www.bmmedical.ui.Informations.InformationsActivty;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class ApprovalsActivity extends AppCompatActivity {
    private static final int LOGIN_REQUEST = 1;
    ActivityApprovalsBinding binding;
    LoginViewModel loginViewModel;

    RecyclerView approvalRecyclerView;
    MenuAdapter approvalMenuAdapter;
    LinearLayoutManager linearLayoutManager;
    List<MenuItem> approvalMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_approvals);
        binding.setLifecycleOwner(this);

        approvalRecyclerView = binding.approvalRecyclerView;
        approvalMenuAdapter = new MenuAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        approvalMenuList = new ArrayList<>();
        approvalMenuAdapter.setMenuItems(approvalMenuList);
        approvalMenuAdapter.setAdapterTypeApprovalMenu();

        approvalRecyclerView.setAdapter(approvalMenuAdapter);
        approvalRecyclerView.setLayoutManager(linearLayoutManager);

        approvalRecyclerView.setHasFixedSize(true);
        approvalRecyclerView.setItemViewCacheSize(20);
        approvalRecyclerView.setDrawingCacheEnabled(true);
        approvalRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        approvalRecyclerView.setNestedScrollingEnabled(false);


        approvalMenuList.add(new MenuItem("",getString(R.string.contractors_desc),R.drawable.contractor_icon));
        approvalMenuAdapter.notifyDataSetChanged();



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
                    startActivityForResult(new Intent(ApprovalsActivity.this, LoginActivity.class), LOGIN_REQUEST);
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
