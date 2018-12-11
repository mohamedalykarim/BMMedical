package com.banquemisr.www.bmmedical.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.RowClinicItemBinding;
import com.banquemisr.www.bmmedical.databinding.RowHospitalItemBinding;
import com.banquemisr.www.bmmedical.databinding.RowMenuItemBinding;
import com.banquemisr.www.bmmedical.ui.Requests.Model.MedicalEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CLINIC_TYPE = R.layout.row_clinic_item;
    private static final int HOSPITAL_TYPE = R.layout.row_hospital_item;



    List<MedicalEntity> entities;
    Context context;

    public EntityRecyclerAdapter(Context context) {
        this.context = context;
        entities = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        if(viewType==CLINIC_TYPE){
            RowClinicItemBinding rowClinicItemBinding = RowClinicItemBinding.inflate(layoutInflater, viewGroup, false);
            return new EntityClincViewHolder(rowClinicItemBinding);
        }else if(viewType ==HOSPITAL_TYPE){
            RowHospitalItemBinding rowHospitalItemBinding = RowHospitalItemBinding.inflate(layoutInflater, viewGroup, false);
            return new EntityHospitalViewHolder(rowHospitalItemBinding);
        }

        RowClinicItemBinding rowClinicItemBinding = RowClinicItemBinding.inflate(layoutInflater, viewGroup, false);
        return new EntityClincViewHolder(rowClinicItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MedicalEntity medicalEntity = entities.get(i);

    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    class EntityClincViewHolder extends RecyclerView.ViewHolder{

        RowClinicItemBinding binding;

        public EntityClincViewHolder(@NonNull RowClinicItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    class EntityHospitalViewHolder extends RecyclerView.ViewHolder{

        RowHospitalItemBinding binding;

        public EntityHospitalViewHolder(@NonNull RowHospitalItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    @Override
    public int getItemViewType(int position) {
         String type = entities.get(position).getType();

         if(null != type){
             switch (type){
                 case "clinic":
                     return R.layout.row_clinic_item;
                 case "hospital":
                     return R.layout.row_hospital_item;
             }
         }

        return super.getItemViewType(position);
    }

    public void setEntities(List<MedicalEntity> entities) {
        this.entities = entities;
    }
}
