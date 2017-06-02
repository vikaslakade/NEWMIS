package com.seedinfotech.newmis;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import commonmodules.ProDailogs;
import clinicpojo.ConnectivityCheck;
import clinicpojo.Constants;
import request.handelers.HTTPclient;

public class MainActivity extends AppCompatActivity {
    EditText username; // username id
    EditText password;  //password iid
   // public static  String validation="http://14.141.60.217/validation";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

    }

    public void  validatelogin(View v)
    {
        if(username.getText().length()==0&&password.getText().length()==0)
        {
            Toast.makeText(v.getContext(),"Please fill details",Toast.LENGTH_LONG).show();
        }
        else
        {
            try {
                String u= username.getText().toString();
                String p= password.getText().toString();
                JSONObject o=new JSONObject();
                o.put("username",u);
                o.put("password",p);
                if(ConnectivityCheck.haveNetworkConnection(getApplicationContext()))
                {
                    new ValidateLogin().execute(o.toString());
                }
                else
                {
                    Toast.makeText(getApplicationContext()," Please check Connectivity",Toast.LENGTH_LONG).show();
                }

//                new ValidateLogin().execute(o.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    //Validation Asyn Task
    public class ValidateLogin extends AsyncTask<String ,String,String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProDailogs.showDailog(MainActivity.this);
        }

        @Override
        protected String doInBackground(String... params) {
            String responce=   HTTPclient.PostData(Constants.validation,params[0]);
            return responce;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ProDailogs.dismissDailog(MainActivity.this);

            Log.d("Validation responce","Validation responce  "+s);
            if(s.trim().equals("Error"))
            {
                Toast.makeText(getApplicationContext(),"Please enter valid credentials",Toast.LENGTH_LONG).show();
            }
            else
            {
                Log.d("RESPONCE",""+s);
              // Toast.makeText(getApplicationContext(),"RESPONCE"+s,Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(),Dashboard.class).putExtra("clinics",s).putExtra("username",username.getText().toString()));

                //startActivity(new Intent(getApplicationContext(),ReportActivity.class).putExtra("clinics",s));
            }

        }
    }
}
