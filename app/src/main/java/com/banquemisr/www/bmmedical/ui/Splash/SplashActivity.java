package com.banquemisr.www.bmmedical.ui.Splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.banquemisr.www.bmmedical.R;
import com.banquemisr.www.bmmedical.ui.MainScreen.MainScreenActivity;
import com.banquemisr.www.bmmedical.ui.login.LoginActivity;
import com.banquemisr.www.bmmedical.utilities.InjectorUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        InjectorUtils.provideAppExecuter().diskIO().execute(()->{
            try {
                Thread.sleep(1000);
                startActivity(new Intent(this,MainScreenActivity.class));
                finish();
            } catch (InterruptedException e) {

            }
        });
    }
}
