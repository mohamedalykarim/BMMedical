package com.banquemisr.www.bmmedical.ui.request_details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityRequestDetailsBinding;
import com.banquemisr.www.bmmedical.ui.entity_location.EntityMapsActivity;
import com.banquemisr.www.bmmedical.ui.requests.RequestViewModel;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class RequestDetailsActivity extends AppCompatActivity {
    private static final String ENTITY_ID = "entity_id";
    private static final String ENTITY_NAME = "entity_name";
    private static final String ENTITY_LAT = "entity_lat";
    private static final String ENTITY_LAN = "entity_lan";
    private String entityID;
    private RequestDetailsViewModel requestDetailsViewModel;
    ActivityRequestDetailsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_request_details);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#dadada"));
        setSupportActionBar(toolbar);



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
                getSupportActionBar().setTitle(entity.getName());

                requestDetailsViewModel.pressMap.observe(this,isPressed->{
                    Intent intent = new Intent(this, EntityMapsActivity.class);
                    intent.putExtra(ENTITY_NAME,entity.getName());
                    intent.putExtra(ENTITY_LAT,entity.getLatitude());
                    intent.putExtra(ENTITY_LAN,entity.getLongitude());

                    startActivity(intent);
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

}
