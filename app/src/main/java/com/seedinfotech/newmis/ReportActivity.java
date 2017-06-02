package com.seedinfotech.newmis;

import android.content.Intent;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.Toast;

import clinicpojo.ConnectivityCheck;


public class ReportActivity extends AppCompatActivity {
public  static  String  TAG="ReportActivity";

 /*   public static String urlService="http://14.141.60.217/service";
    public static  String patientService="http://14.141.60.217/Registered";
    public static  String revenueService="http://14.141.60.217/Revenue";*/



    Bundle b;   // cariies data Clinic ID,From date,To date


    String CLINICS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.select_report_new1);
        Toolbar t=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(t);
        t.setNavigationIcon(R.drawable.ic_drawable_back);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

       //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b=getIntent().getExtras();
        CLINICS = b.get("clinics").toString();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void serviceWiseReport(View v)
       {
           if(ConnectivityCheck.haveNetworkConnection(getApplicationContext()))
           {

               Intent in=new Intent(this,SelectActivity.class);
               in.putExtra("clinics",CLINICS);
               in.putExtra("flag",1);
               startActivity(in);

             //  new MISdata().execute( b.getString("InputJsonString"));
           }
           else
           {
               Toast.makeText(getApplicationContext(),"Please ckeck connectivity",Toast.LENGTH_LONG).show();
           }


       }
    public void get_reg_data(View view){

        if(ConnectivityCheck.haveNetworkConnection(getApplicationContext()))
        {

            Intent in=new Intent(this,SelectActivity.class);
            in.putExtra("clinics",CLINICS);
            in.putExtra("flag",2);
            startActivity(in);


        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please ckeck connectivity",Toast.LENGTH_LONG).show();
        }


    }
    public  void revenudata(View view)
    {

        if(ConnectivityCheck.haveNetworkConnection(getApplicationContext()))
        {

            Intent in=new Intent(this,SelectActivity.class);
            in.putExtra("clinics",CLINICS);
            in.putExtra("flag",3);
            startActivity(in);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please ckeck connectivity",Toast.LENGTH_LONG).show();
        }


    }


    }

