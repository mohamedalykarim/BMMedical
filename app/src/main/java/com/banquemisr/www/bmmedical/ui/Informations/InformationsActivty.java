package com.banquemisr.www.bmmedical.ui.Informations;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.banquemisr.www.bmmedical.Adapters.MenuAdapter;
import com.banquemisr.www.bmmedical.data.model.MenuItem;
import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityInformationsActivtyBinding;
import com.banquemisr.www.bmmedical.ui.MainScreen.MainScreenActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModel;
import com.banquemisr.www.bmmedical.ui.login.LoginViewModelFactory;
import com.banquemisr.www.bmmedical.utilities.FirebaseUtils;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

public class InformationsActivty extends AppCompatActivity {
    private static final int LOGIN_REQUEST = 1;
    ActivityInformationsActivtyBinding binding;
    LoginViewModel loginViewModel;

    RecyclerView menuInformationRecycler;
    MenuAdapter menuInformationAdapter;
    LinearLayoutManager menuInformationLinearLayoutManager;
    List<MenuItem> menuItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_informations_activty);

        binding.setLifecycleOwner(this);

        menuInformationRecycler = binding.informationRecyclerView;
        menuInformationAdapter = new MenuAdapter(this);
        menuInformationLinearLayoutManager = new LinearLayoutManager(this);
        menuItems = new ArrayList<>();
        menuInformationAdapter.setMenuItems(menuItems);
        menuInformationAdapter.setAdapterTypeInformationMenu();
        menuInformationRecycler.setAdapter(menuInformationAdapter);
        menuInformationRecycler.setLayoutManager(menuInformationLinearLayoutManager);

        menuInformationRecycler.setHasFixedSize(true);
        menuInformationRecycler.setItemViewCacheSize(20);
        menuInformationRecycler.setDrawingCacheEnabled(true);
        menuInformationRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        menuInformationRecycler.setNestedScrollingEnabled(false);


        menuItems.add(new MenuItem(getString(R.string.glass_grant),getString(R.string.glasses_grant_desc),R.drawable.glasses_icon));
        menuItems.add(new MenuItem(getString(R.string.teeth_implant),getString(R.string.teeth_implant_desc),R.drawable.teeth_implant));
        menuItems.add(new MenuItem(getString(R.string.childbirth_grant),getString(R.string.childbirth_grant_desc),R.drawable.birth_icon));
        menuItems.add(new MenuItem(getString(R.string.chronic_diseases),getString(R.string.chronic_diseases_desc),R.drawable.chronic_diseases_icon));
        menuItems.add(new MenuItem(getString(R.string.absence),getString(R.string.absence_desc),R.drawable.absence_icon));
        menuItems.add(new MenuItem(getString(R.string.contractors),getString(R.string.contractors_desc),R.drawable.contractor_icon));
        menuItems.add(new MenuItem(getString(R.string.doc),getString(R.string.doc_desc),R.drawable.documentation_icon));
        menuInformationAdapter.notifyDataSetChanged();

        /**
         *  Login View Model
         */

        LoginViewModelFactory factory = InjectorUtils.provideLoginViewModelFactory(this);
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);

        getOutifNotLogin();
        getUserDetails();

    }



    public void getOutifNotLogin(){
        loginViewModel.login.getLoginPressedEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    startActivityForResult(new Intent(InformationsActivty.this, LoginActivity.class), LOGIN_REQUEST);
                }
            }
        });


    }

    void getUserDetails(){
        loginViewModel.getUser().observe(this, newUser->{
            binding.setUser(newUser);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(null == FirebaseUtils.provideFirebaseAuth().getCurrentUser()){
            loginViewModel.login.setLogged(false);
            loginViewModel.login.setLoginPressedEvent(false);
        }else{
            loginViewModel.login.setLogged(true);
            loginViewModel.login.setLoginPressedEvent(false);
        }
    }
}
