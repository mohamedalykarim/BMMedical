package com.banquemisr.www.bmmedical.ui.entity_type;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banquemisr.www.bmmedical.Adapters.SpecializationAdapter;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityEntityTypesBinding;
import com.banquemisr.www.bmmedical.ui.MainScreen.MainScreenActivity;
import com.banquemisr.www.bmmedical.ui.entity_type.model.Specialization;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;
import com.banquemisr.www.bmmedical.ui.requests.RequestsActivity;
import com.banquemisr.www.bmmedical.ui.transaction.TransactionDetailsActivity;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityTypesActivity extends AppCompatActivity implements SpecializationAdapter.OnItemClicked {
    private static final String MY_REQUEST_ID = "my_request_id";
    private static final int LOGIN_REQUEST = 1;
    LoginViewModel loginViewModel;
    ActivityEntityTypesBinding binding;
    public static EntityTypeVM entityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_entity_types);
        binding.setLifecycleOwner(this);

        /**
         *  Login View Model
         */

        LoginViewModelFactory factory = InjectorUtils.provideLoginViewModelFactory(this);
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);



        getOutifNotLogin();
        getUserDetails();

        EntityTypeVmFactory entityTypeVmFactory = InjectorUtils.provideEntityTypeVMFactory();
        entityViewModel = ViewModelProviders.of(this, entityTypeVmFactory).get(EntityTypeVM.class);
        entityViewModel.setValues();

        binding.setEntityTypeVM(entityViewModel);


        ListView specializationListView = binding.specializationList;
        List<Specialization> specializationList = new ArrayList<>();
        specializationList.add(new Specialization("اطفال", R.drawable.baby_icon));
        specializationList.add(new Specialization("عظام", R.drawable.bones_icon));
        specializationList.add(new Specialization( "باطنة", R.drawable.stomach_icon));

        SpecializationAdapter specializationAdapter = new SpecializationAdapter(this,specializationList, this);
        specializationListView.setAdapter(specializationAdapter);
    }

    public void getOutifNotLogin(){
        loginViewModel.login.getLoginPressedEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    startActivityForResult(new Intent(EntityTypesActivity.this, LoginActivity.class), LOGIN_REQUEST);
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
                                        Intent intent = new Intent(EntityTypesActivity.this, TransactionDetailsActivity.class);
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


    @Override
    public void OnSpecializationItemClicked(String specialization) {
        entityViewModel.specialization.setValue(specialization);
        startActivity(new Intent(this, RequestsActivity.class));
        finish();
    }
}
