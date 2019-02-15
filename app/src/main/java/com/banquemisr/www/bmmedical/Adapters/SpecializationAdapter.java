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
import com.banquemisr.www.bmmedical.ui.entity_type.model.Specialization;

import java.util.List;

public class SpecializationAdapter extends ArrayAdapter<Specialization> {
    OnItemClicked onItemClicked;

    public SpecializationAdapter(@NonNull Context context, List<Specialization> specializationList, OnItemClicked onItemClicked) {
        super(context, 0, specializationList);
        this.onItemClicked = onItemClicked;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.row_specialization_list_item,parent,false);

        Specialization specialization = getItem(position);

        ImageView imageView = view.findViewById(R.id.image);
        TextView textView = view.findViewById(R.id.text);

        imageView.setImageResource(specialization.getResource());
        textView.setText(specialization.getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked.OnSpecializationItemClicked(specialization.getName());
            }
        });


        return view;
    }

    public interface OnItemClicked{
        public void OnSpecializationItemClicked(String specialization);
    }


}
