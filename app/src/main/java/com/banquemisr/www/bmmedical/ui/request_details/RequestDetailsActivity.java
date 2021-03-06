package com.banquemisr.www.bmmedical.ui.request_details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityRequestDetailsBinding;
import com.banquemisr.www.bmmedical.ui.MainScreen.MainScreenActivity;
import com.banquemisr.www.bmmedical.ui.entity_location.EntityMapsActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.transaction.TransactionDetailsActivity;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;


public class RequestDetailsActivity extends AppCompatActivity {
    private static final String ENTITY_ID = "entity_id";
    private static final String ENTITY_NAME = "entity_name";
    private static final String ENTITY_LAT = "entity_lat";
    private static final String ENTITY_LAN = "entity_lan";
    private static final int LOGIN_REQUEST = 1;
    private static final String MY_REQUEST_ID = "my_request_id";

    private String entityID;
    private RequestDetailsViewModel requestDetailsViewModel;
    ActivityRequestDetailsBinding binding;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_request_details);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#dadada"));
        setSupportActionBar(toolbar);

        /**
         *  Login View Model
         */

        LoginViewModelFactory factory = InjectorUtils.provideLoginViewModelFactory(this);
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);

        getOutifNotLogin();
        getUserDetails();




        Intent oldIntent = getIntent();
        if(null != oldIntent && oldIntent.hasExtra(ENTITY_ID)){
            entityID = oldIntent.getStringExtra(ENTITY_ID);

            RequestDetailsViewModelFactory requestDetailsViewModelFactory = InjectorUtils.provideRequestDetailViewModelFactory(
                    this,
                    entityID
                    );

            requestDetailsViewModel = ViewModelProviders
                    .of(this,requestDetailsViewModelFactory)
                    .get(RequestDetailsViewModel.class);

            binding.setRequestDetailsViewModel(requestDetailsViewModel);

            requestDetailsViewModel.medicalEntity.observe(this, entity->{
                binding.setMedicalEntity(entity);

                if(entity.getType().equals("hospital")){
                    binding.requestTypeImage.setImageResource(R.drawable.hospital_icon);
                }else if(entity.getType().equals("clinic")){
                    binding.requestTypeImage.setImageResource(R.drawable.clinic_icon);
                }

                if(null != entity)
                getSupportActionBar().setTitle(entity.getName());

                requestDetailsViewModel.pressMap.observe(this,isPressed->{
                    Intent intent = new Intent(this, EntityMapsActivity.class);
                    intent.putExtra(ENTITY_NAME,entity.getName());
                    intent.putExtra(ENTITY_LAT,entity.getLatitude());
                    intent.putExtra(ENTITY_LAN,entity.getLongitude());

                    startActivity(intent);
                });


                requestDetailsViewModel.getRequestsWithin15(entity.getSpecialist()).observe(this,existsRequests->{
                    if(null != existsRequests){

                        if(existsRequests.size() < 1){
                            requestDetailsViewModel.isExistTransformation.setValue(false);
                        }else {
                            requestDetailsViewModel.isExistTransformation.setValue(true);
                        }
                    }
                });

            });

            requestDetailsViewModel.pressAskForMedicalRequest.observe(this,pressed->{
                askForRequestAlertDialog();
            });




        }





    }

    void askForRequestAlertDialog(){
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle(R.string.ask_for_medical_request_title_alert);
        builder.setMessage(getString(R.string.are_you_sure_of_asking_for_medical_request)+ requestDetailsViewModel.medicalEntity.getValue().getName());

        builder.setPositiveButton(R.string.yes_i_m, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * add the request
                 */
                requestDetailsViewModel.addTheMedicalRequest(loginViewModel.getUser().getValue(), getApplicationContext());

                finish();

            }
        });

        builder.setNegativeButton(R.string.ni_iam_not, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();


    }

    public void getOutifNotLogin(){
        loginViewModel.login.getLoginPressedEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    startActivityForResult(new Intent(RequestDetailsActivity.this, LoginActivity.class), LOGIN_REQUEST);
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
                                        Intent intent = new Intent(RequestDetailsActivity.this,TransactionDetailsActivity.class);
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
