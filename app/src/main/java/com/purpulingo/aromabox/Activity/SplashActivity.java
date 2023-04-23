package com.purpulingo.aromabox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.purpulingo.aromabox.Global.UserSessionManager;
import com.purpulingo.aromabox.R;

public class SplashActivity extends AppCompatActivity {

    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userSessionManager = new UserSessionManager(getApplicationContext());
        //night mode off
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                final ProgressDialog progressDialog = new ProgressDialog(SplashActivity.this);
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();

                if(userSessionManager.getLogin()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    //finish();
                }else {
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    //finish();
                }
                finish();
            }
        },2000);
    }
}