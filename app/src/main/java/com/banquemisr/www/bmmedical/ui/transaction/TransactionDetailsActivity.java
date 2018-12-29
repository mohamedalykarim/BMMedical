package com.banquemisr.www.bmmedical.ui.transaction;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.databinding.ActivityTransactionDetailsBinding;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class TransactionDetailsActivity extends AppCompatActivity {
    private static final String MY_REQUEST_ID = "my_request_id";

    ActivityTransactionDetailsBinding binding;
    private TransactionDetailsViewModel transactionVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_transaction_details);
        binding.setLifecycleOwner(this);

        Toolbar toolbar = binding.toolbar;
        toolbar.setTitleTextColor(getResources().getColor(R.color.da));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.transformation);


        TransactionDetailsVmFactory factory = InjectorUtils.provideTransactionFactory(this);
        transactionVM = ViewModelProviders.of(this,factory).get(TransactionDetailsViewModel.class);

        Intent oldIntent = getIntent();

        if(oldIntent.hasExtra(MY_REQUEST_ID)){
            String request_id = oldIntent.getStringExtra(MY_REQUEST_ID);

            transactionVM.getTransactionDetailsById(request_id).observe(this, transaction->{
                if(null != transaction){

                    binding.setRequest(transaction);
                    transactionVM.getMedicalEntity(transaction.getContractorId()).observe(this, entity->{
                        if(null != entity)
                        binding.setEntity(entity);
                    });

                    transactionVM.getUser(transaction.getOracle()).observe(this, user->{
                        if(null != user)

                        binding.setUser(user);
                    });
                }
            });
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.transformation_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.print_menu_item:
                Bitmap bitmap = getBitmapFromView(binding.myRequest);
                doPhotoPrint(bitmap);

                break;


        }

        return super.onOptionsItemSelected(item);
    }


    private void doPhotoPrint(Bitmap bitmap) {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }


    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }




}
