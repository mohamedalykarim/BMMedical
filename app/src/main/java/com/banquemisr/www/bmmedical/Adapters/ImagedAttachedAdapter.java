package com.banquemisr.www.bmmedical.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.banquemisr.www.bmmedical.R;

import java.util.List;

public class ImagedAttachedAdapter extends ArrayAdapter<Uri> {

    public ImagedAttachedAdapter(@NonNull Context context, List<Uri> uris) {
        super(context, 0, uris);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.row_image_attached,parent,false);
        ImageView imageView = view.findViewById(R.id.image);

        Uri uri = getItem(position);
        imageView.setImageURI(uri);

        return view;

    }
}
