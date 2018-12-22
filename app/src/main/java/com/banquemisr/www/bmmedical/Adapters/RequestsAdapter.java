package com.banquemisr.www.bmmedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.ui.request_details.model.RequestDetails;

import java.util.List;

public class RequestsAdapter extends ArrayAdapter<RequestDetails> {


    public RequestsAdapter(@NonNull Context context, @NonNull List<RequestDetails> requestDetails) {
        super(context, 0, requestDetails);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.row_request_list_item,parent,false);
        RequestDetails requestDetails = getItem(position);

        TextView name = view.findViewById(R.id.name_tv);
        name.setText(getContext().getResources().getString(R.string.medical_request_title)+requestDetails.getName());

        return view;
    }
}
