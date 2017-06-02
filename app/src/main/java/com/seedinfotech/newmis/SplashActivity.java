package com.seedinfotech.newmis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import commonmodules.ProDailogs;

public class SplashActivity extends AppCompatActivity {
ProgressDialog pd;
    //add comment for mis match report
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // pd=new ProgressDialog(this,R.style.CustomDialogTheme);
        //pd.setCancelable(false);
       // pd.show();
        ProDailogs.showDailog(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                ProDailogs.dismissDailog(getApplicationContext());
                finish();
            }
        }, 3000);
    }
}

