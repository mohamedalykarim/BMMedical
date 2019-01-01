package com.banquemisr.www.bmmedical.ui.requests;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.ui.requests.model.Filter;

public class FilterDialog extends Dialog {
    RequestViewModel requestViewModel;
    public FilterDialog(@NonNull Context context, RequestViewModel requestViewModel) {
        super(context);
        this.requestViewModel = requestViewModel;
    }

    public FilterDialog(@NonNull Context context, int themeResId, RequestViewModel requestViewModel) {
        super(context, themeResId);
        this.requestViewModel = requestViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_filter);
        CheckBox hospital = findViewById(R.id.hospitalCheckBox);
        CheckBox clinic = findViewById(R.id.clinicCheckBox);
        Spinner spinner = findViewById(R.id.spec_spinner);
        Button submit = findViewById(R.id.submit);

        Filter filter = new Filter();
        requestViewModel.filter.setValue(filter);

        hospital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(null != requestViewModel.filter.getValue())
                requestViewModel.filter.getValue().setHospital(isChecked);
            }
        });

        clinic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(null != requestViewModel.filter.getValue())
                requestViewModel.filter.getValue().setClinic(isChecked);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(null != requestViewModel.filter.getValue())
                requestViewModel.filter.getValue().setSpecialization(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter filter = requestViewModel.filter.getValue();
                requestViewModel.filter.setValue(filter);
                dismiss();
            }
        });

    }
}
