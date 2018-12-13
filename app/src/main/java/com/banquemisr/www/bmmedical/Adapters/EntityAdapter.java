package com.banquemisr.www.bmmedical.Adapters;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.RowClinicItemBinding;
import com.banquemisr.www.bmmedical.databinding.RowHospitalItemBinding;
import com.banquemisr.www.bmmedical.ui.requests.model.MedicalEntity;


public class EntityAdapter extends PagedListAdapter<MedicalEntity,RecyclerView.ViewHolder> {
    private static final int CLINIC_TYPE = R.layout.row_clinic_item;
    private static final int HOSPITAL_TYPE = R.layout.row_hospital_item;

    RowHospitalItemBinding rowHospitalItemBinding;
    RowClinicItemBinding rowClinicItemBinding;

    public EntityAdapter() {
        super(DIFF_CALLBACK);
    }

    public static DiffUtil.ItemCallback<MedicalEntity> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<MedicalEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull MedicalEntity medicalEntity, @NonNull MedicalEntity t1) {
            return medicalEntity.getId()  == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MedicalEntity medicalEntity, @NonNull MedicalEntity t1) {
            return medicalEntity.equals(t1);
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        if(viewType==CLINIC_TYPE){
            rowClinicItemBinding = RowClinicItemBinding.inflate(layoutInflater, viewGroup, false);
            return new EntityClincViewHolder(rowClinicItemBinding);
        }else if(viewType ==HOSPITAL_TYPE){
            rowHospitalItemBinding = RowHospitalItemBinding.inflate(layoutInflater, viewGroup, false);
            return new EntityHospitalViewHolder(rowHospitalItemBinding);
        }

        RowClinicItemBinding rowClinicItemBinding = RowClinicItemBinding.inflate(layoutInflater, viewGroup, false);
        return new EntityClincViewHolder(rowClinicItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(getItemViewType(i) == CLINIC_TYPE){
            rowClinicItemBinding.setMedicalEntity(getItem(i));
        }else if(getItemViewType(i) == HOSPITAL_TYPE){
            rowHospitalItemBinding.setMedicalEntity(getItem(i));
        }

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
        String type = getItem(position).getType();

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
}
