package com.banquemisr.www.bmmedical.ui.show_approval_details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banquemisr.www.bmmedical.Adapters.HorizontalImageAttachedAdapter;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityApprovalDetailsBinding;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.RequestsActivity;
import com.banquemisr.www.bmmedical.ui.transaction.TransactionDetailsActivity;
import com.banquemisr.www.bmmedical.utilities.ApprovalUtils;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class ApprovalDetailsActivity extends AppCompatActivity {
    private static final String APPROVAL_ID = "approval_id";
    private static final String MY_REQUEST_ID = "my_request_id";
    private static final int LOGIN_REQUEST = 1;
    ActivityApprovalDetailsBinding binding;
    private LoginViewModel loginViewModel;
    private ApprovalDetailsVM approvalDetailsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_approval_details);

        Toolbar toolbar = binding.toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.da));
        setSupportActionBar(toolbar);


        Intent oldIntent = getIntent();
        if(oldIntent.hasExtra(APPROVAL_ID)){

            String id = oldIntent.getStringExtra(APPROVAL_ID);

            /**
             * Login View Model
             */

            LoginViewModelFactory factory = InjectorUtils.provideLoginViewModelFactory(this);
            loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
            binding.setLoginViewModel(loginViewModel);

            getOutifNotLogin();
            getUserDetails();

            /**
             * Approval Details View Model
             */
            
            ApprovalDetailsFactory approvalDetailsFactory = InjectorUtils.provideApprovalVMFactory(this);
            approvalDetailsViewModel = ViewModelProviders.of(this, approvalDetailsFactory).get(ApprovalDetailsVM.class);


            approvalDetailsViewModel.getApprovalDetails(id).observe(this,approval -> {
                getSupportActionBar().setTitle(ApprovalUtils.convertMedicalTypeToResource(approval.getType(),this));
                chooseEmptyImage(ApprovalUtils.convertMedicalTypeToResource(approval.getType(),this));
                binding.setApproval(approval);
                binding.typeTv.setText(ApprovalUtils.convertMedicalTypeToResource(approval.getType(),this));
            });


            loginViewModel.getUser().observe(this, user->{

                /**
                 * Attachments
                 */

                RecyclerView attachmentRecycler = binding.attachedImages;
                LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
                HorizontalImageAttachedAdapter attachedAdapter = new HorizontalImageAttachedAdapter();
                attachedAdapter.setId(id);
                attachedAdapter.setOracle(user.getOracle()+"");

                attachmentRecycler.setLayoutManager(layoutManager);
                attachmentRecycler.setAdapter(attachedAdapter);

                approvalDetailsViewModel.getImagesNames(id).observe(this, images->{
                    attachedAdapter.setUris(images);
                    attachedAdapter.notifyDataSetChanged();
                });


            });

        }
    }

    public void getOutifNotLogin(){
        loginViewModel.login.getLoginPressedEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    startActivityForResult(new Intent(ApprovalDetailsActivity.this, LoginActivity.class), LOGIN_REQUEST);
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
                                        Intent intent = new Intent(ApprovalDetailsActivity.this, TransactionDetailsActivity.class);
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

    public void chooseEmptyImage(String type){
        if(type.equals(getResources().getString(R.string.medical_analysis_title))){
            binding.approvalTypeImage.setImageResource(R.drawable.medical_analysis_icon);
        }else if(type.equals(getResources().getString(R.string.medical_rays_title))){
            binding.approvalTypeImage.setImageResource(R.drawable.medical_rays_icon);
        }else if(type.equals(getResources().getString(R.string.physical_therapy_title))){
            binding.approvalTypeImage.setImageResource(R.drawable.physical_therapy_icon);
        }else if(type.equals(getResources().getString(R.string.dental_approval_title))){
            binding.approvalTypeImage.setImageResource(R.drawable.dental);
        }else if(type.equals(getResources().getString(R.string.hospitalization_non_title))){
            binding.approvalTypeImage.setImageResource(R.drawable.hospitalization_icon);
        }else if(type.equals(getResources().getString(R.string.hospitalization_title))){
            binding.approvalTypeImage.setImageResource(R.drawable.hospitalizaton2_icon);
        }else if(type.equals(getResources().getString(R.string.chemotherapy_title))){
            binding.approvalTypeImage.setImageResource(R.drawable.chemotherapy_icon);
        }else if(type.equals(getResources().getString(R.string.others_approval_title))){
            binding.approvalTypeImage.setImageResource(R.drawable.other_medical_icon);
        }
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
