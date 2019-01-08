package com.banquemisr.www.bmmedical.ui.approvals_menu;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.banquemisr.www.bmmedical.Adapters.MenuAdapter;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.data.model.MenuItem;
import com.banquemisr.www.bmmedical.databinding.ActivityApprovalsBinding;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.transaction.TransactionDetailsActivity;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class ApprovalsMenuActivity extends AppCompatActivity {
    private static final int LOGIN_REQUEST = 1;
    private static final String MY_REQUEST_ID = "my_request_id";
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


        approvalMenuList.add(new MenuItem(getString(R.string.medical_analysis_title),getString(R.string.medical_analysis_desc),R.drawable.medical_analysis_icon));
        approvalMenuList.add(new MenuItem(getString(R.string.medical_rays_title),getString(R.string.medical_rays_desc),R.drawable.medical_rays_icon));
        approvalMenuList.add(new MenuItem(getString(R.string.physical_therapy_title),getString(R.string.physical_therapy_desc),R.drawable.physical_therapy_icon));
        approvalMenuList.add(new MenuItem(getString(R.string.dental_approval_title),getString(R.string.dental_approval_desc),R.drawable.dental));
        approvalMenuList.add(new MenuItem(getString(R.string.hospitalization_non_title),getString(R.string.hospitalization_non_desc),R.drawable.hospitalization_icon));
        approvalMenuList.add(new MenuItem(getString(R.string.hospitalization_title),getString(R.string.hospitalization_desc),R.drawable.hospitalizaton2_icon));
        approvalMenuList.add(new MenuItem(getString(R.string.chemotherapy_title),getString(R.string.chemotherapy_desc),R.drawable.chemotherapy_icon));
        approvalMenuList.add(new MenuItem(getString(R.string.others_approval_title),getString(R.string.others_approval_desc),R.drawable.other_medical_icon));
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
                    startActivityForResult(new Intent(ApprovalsMenuActivity.this, LoginActivity.class), LOGIN_REQUEST);
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
                            binding.navMain.requestsList.removeAllViews();

                            for (RequestDetails requestDetails: requests){
                                View view = getLayoutInflater().inflate(R.layout.row_request_list_item,null,false);
                                TextView name = view.findViewById(R.id.name_tv);
                                ImageView image = view.findViewById(R.id.image);
                                name.setText(getResources().getString(R.string.medical_request_title)+requestDetails.getName());

                                loginViewModel.getEntityById(requestDetails.getContractorId())
                                        .observe(this, entity->{

                                            if(null != entity){
                                                if(entity.getType().equals("hospital")){
                                                    image.setImageResource(R.drawable.hospital_icon);
                                                }else if(entity.getType().equals("clinic")){
                                                    image.setImageResource(R.drawable.clinic_icon);
                                                }
                                            }

                                        });


                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(ApprovalsMenuActivity.this,TransactionDetailsActivity.class);
                                        intent.putExtra(MY_REQUEST_ID, requestDetails.getId());
                                        startActivity(intent);
                                    }
                                });

                                binding.navMain.requestsList.addView(view);
                            }
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
}
