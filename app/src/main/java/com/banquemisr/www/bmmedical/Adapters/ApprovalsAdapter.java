package com.banquemisr.www.bmmedical.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.ui.show_approvals.model.Approval;

import java.util.List;

public class ApprovalsAdapter extends ArrayAdapter<Approval> {


    public ApprovalsAdapter(@NonNull Context context, @NonNull List<Approval> approvals) {
        super(context, 0, approvals);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.row_approval_list_item,parent,false);

        Approval approval = getItem(position);

        TextView name = view.findViewById(R.id.name_tv);
        ImageView imageView = view.findViewById(R.id.image);

        name.setText(approval.getType());

        if(approval.getStatus().equals("sent to medical")){
            imageView.setImageResource(R.drawable.not_approved_icon);
        }else if(approval.getStatus().equals("approved")){
            imageView.setImageResource(R.drawable.approved_icon);
        }else if(approval.getStatus().equals("rejected")){
            imageView.setImageResource(R.drawable.rejected_icon);
        }

        return view;
    }
}
