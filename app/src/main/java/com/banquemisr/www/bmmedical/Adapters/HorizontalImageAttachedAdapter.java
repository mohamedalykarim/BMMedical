package com.banquemisr.www.bmmedical.Adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HorizontalImageAttachedAdapter extends RecyclerView.Adapter<HorizontalImageAttachedAdapter.ViewHolder> {
    List<String> uris;
    String id;
    String oracle;

    public HorizontalImageAttachedAdapter() {
        uris = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.row_image_attached, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FirebaseUtils.getAttachedImagesForApprovalStorageReference(oracle).child(id).child(uris.get(i))
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               Picasso.get().load(uri).placeholder(R.drawable.documentation_icon).into(viewHolder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOracle(String oracle) {
        this.oracle = oracle;
    }
}
