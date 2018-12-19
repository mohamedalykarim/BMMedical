package com.banquemisr.www.bmmedical.ui.request_details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityRequestDetailsBinding;
import com.banquemisr.www.bmmedical.ui.entity_location.EntityMapsActivity;
import com.banquemisr.www.bmmedical.ui.requests.RequestViewModel;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class RequestDetailsActivity extends AppCompatActivity {
    private static final String ENTITY_ID = "entity_id";
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

            requestDetailsViewModel.medicalEntity.observe(this, entity->{
                binding.setMedicalEntity(entity);
                getSupportActionBar().setTitle(entity.getName());

            });


        }

        startActivity(new Intent(this, EntityMapsActivity.class));

    }

}
