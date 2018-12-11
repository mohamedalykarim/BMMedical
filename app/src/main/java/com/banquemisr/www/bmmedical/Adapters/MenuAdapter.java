package com.banquemisr.www.bmmedical.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banquemisr.www.bmmedical.data.model.MenuItem;
import com.banquemisr.www.bmmedical.ui.Approvals.ApprovalsActivity;
import com.banquemisr.www.bmmedical.ui.Informations.InformationsActivty;
import com.banquemisr.www.bmmedical.ui.Requests.RequestsActivity;
import com.banquemisr.www.bmmedical.databinding.RowMenuItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private static final String MAIN_MENU_TYPE = "main_menu_type";
    private static final String INFORMATION_MENU_TYPE = "information_menu_type";
    private static final String APPROVAL_MENU_TYPE = "approval_menu_type";


    List<MenuItem> menuItems;
    Context context;
    String adapterType;

    public MenuAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        RowMenuItemBinding rowMenuItemBinding = RowMenuItemBinding.inflate(layoutInflater,viewGroup,false);
        return new MenuViewHolder(rowMenuItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i) {
        MenuItem menuItem = menuItems.get(i);

        menuViewHolder.bind(menuItem);


        menuViewHolder.binding.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * If main menu handle clicks
                  */
                if (adapterType.equals(MAIN_MENU_TYPE)){
                    final Intent intent;

                    switch (i){
                        case 0:
                            intent =  new Intent(context, InformationsActivty.class);
                            break;

                        case 1:
                            intent =  new Intent(context, RequestsActivity.class);
                            break;

                        default:
                            intent =  new Intent(context, ApprovalsActivity.class);
                            break;
                    }
                    context.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder{
        RowMenuItemBinding binding;

        public MenuViewHolder(@NonNull RowMenuItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind (MenuItem menuItem){
            binding.setMenuItem(menuItem);

            Picasso
                    .get()
                    .load(menuItem.getImageId())
                    .fit()
                    .centerCrop()
                    .into(binding.imageView3);
        }
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setAdapterTypeMainMenu() {
        this.adapterType = MAIN_MENU_TYPE;
    }

    public void setAdapterTypeInformationMenu() {
        this.adapterType = INFORMATION_MENU_TYPE ;
    }

    public void setAdapterTypeApprovalMenu() {
        this.adapterType = APPROVAL_MENU_TYPE ;
    }
}
