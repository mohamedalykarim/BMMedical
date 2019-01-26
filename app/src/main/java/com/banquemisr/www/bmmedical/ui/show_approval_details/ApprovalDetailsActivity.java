package com.banquemisr.www.bmmedical.ui.show_approval_details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.banquemisr.www.bmmedical.R;

public class ApprovalDetailsActivity extends AppCompatActivity {
    private static final String APPROVAL_ID = "approval_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_details);

        Intent oldIntent = getIntent();
        if(oldIntent.hasExtra(APPROVAL_ID)){


        }
    }
}
