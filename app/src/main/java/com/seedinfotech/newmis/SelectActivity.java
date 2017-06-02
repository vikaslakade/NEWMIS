package com.seedinfotech.newmis;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


import clinicpojo.ConnectivityCheck;
import clinicpojo.DateWriterinPdf;
import commonmodules.ProDailogs;
import clinicpojo.Clinic;
import clinicpojo.Constants;
import clinicpojo.Daily_Revenue_Final;
import clinicpojo.Finalmodelcalss;
import clinicpojo.InputData;
import clinicpojo.Myhashset;
import clinicpojo.PatientMaps;
import clinicpojo.Patient_Reg_Final;
import clinicpojo.RegistrationPojo;
import clinicpojo.RevenueMap;
import clinicpojo.RevenuePojo;
import clinicpojo.ServiceWiseBillingModel;
import clinicpojo.SummuryReport;
import request.handelers.HTTPclient;

public class SelectActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "SelectActivity";
    ArrayList<Clinic> clinicspinner;
    String selectedClinicCode;
    InputData input;
    ArrayList<String> spinnerData;
    EditText fromdate;
    EditText todate;
    Spinner clinicname;
    boolean status;
    int unitid;
    Gson g;
    String myFormat = "yyyy-MM-dd";




   // public static  String urlService="http://14.141.60.217/service";
   // public static  String patientService="http://14.141.60.217/Registered";
    //public static  String revenueService="http://14.141.60.217/Revenue";
    //public static String urlService="http://115.112.255.101/milannivf_demo/Login.aspx";
    // Final List for next Activity
    static  ArrayList<Patient_Reg_Final> preg; // Final List for next Activity
    static  ArrayList<SummuryReport> reportData;
    static  ArrayList<Daily_Revenue_Final> daily_revenue_finals;

    ArrayList<ServiceWiseBillingModel> list;
    ArrayList<RegistrationPojo> registrationPojoArrayList;
    ArrayList<RevenuePojo> revenuePojoArrayList;


    ArrayList<Finalmodelcalss> c;
    ArrayList<SummuryReport> report;


   // cariies data Clinic ID,From date,To date


    //JSONObject dataToPost;
    String dataToPost;
    String CLINICS;
    int flag;
    boolean misflag=false;
    //long mainDiff;
    Calendar  myCalendar;
    long mainDiff;
    EditText e;
    Bundle b;
    DatePickerDialog.OnDateSetListener date,dateFrom;
    Dialog DateError;
    Date fromDateSelect,toDateSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        fromDateSelect=new Date();
        toDateSelect=new Date();
        e=(EditText) findViewById(R.id.selectTitle);
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
        fromdate=(EditText) findViewById(R.id.from_date);
        todate=(EditText)findViewById(R.id.to_date);
        todate.setKeyListener(null);
        fromdate.setKeyListener(null);
        list=new ArrayList<>();
        c=new ArrayList<>();

        reportData=new ArrayList<>();
        preg=new ArrayList<>();
        daily_revenue_finals=new ArrayList<>();

        report=new ArrayList<>();
        Myhashset.Level1= new HashSet<>();
        Myhashset.Level2= new HashSet<>();
        // Myhashset.Services= new HashSet<>();
        Myhashset.petiont_name=new HashSet<>();
        Myhashset.clinicName=new HashSet<>();


        registrationPojoArrayList=new ArrayList<>();
        revenuePojoArrayList=new ArrayList<>();












       b=getIntent().getExtras();
         CLINICS = b.get("clinics").toString();
        flag=b.getInt("flag");
        if(flag==1)
        {
            e.setText("Service Wise Biling Report");
        }
        if(flag==2)
        {
            e.setText("Patient Registration Report");
        }
        if(flag==3)
        {
            e.setText("Revenue Report");

        }

        spinnerData=new ArrayList<>();
        input=new InputData();
        g=new Gson();
        try {
            setDataToSpinner();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        clinicname.setOnItemSelectedListener(this);

        myCalendar = Calendar.getInstance();

        dateFrom = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelFrom();
            }

        };

        todate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SelectActivity.this, dateFrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });









        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        fromdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SelectActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    public void gobackToselect(View v)
    {
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        fromDateSelect=myCalendar.getTime();
        Log.d("From Date ",  fromDateSelect.toString());
        fromdate.setText(sdf.format(myCalendar.getTime()));


    }


    private void updateLabelFrom() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        toDateSelect=myCalendar.getTime();
        Log.d("to Date ",sdf.format(myCalendar.getTime()));

        todate.setText(sdf.format(myCalendar.getTime()));

     //   searchData();
    }


    public void searchData()
    {

        Date currentDate=new Date();
        int com1=currentDate.compareTo(toDateSelect);
        int com2=currentDate.compareTo(fromDateSelect);




        if((com1==1||com1==0)&&(com2==1||com2==0))
        {
           // todate.setError(null);
            //fromdate.setError(null);


            int com3=fromDateSelect.compareTo(toDateSelect);
            if(com3==-1||com3==0)
            {

                long diff=    toDateSelect.getTime()-fromDateSelect.getTime();
                Log.d("CAll "," "+ TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                mainDiff=  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                if(mainDiff<=30)
                {
                    Log.d("DONE ","DONE");

                    misflag=true;

                }
                else
                {
                    fromdate.setText("");
                    todate.setText("");
                   // todate.setError("you have enter more than 30 days");
                    Toast.makeText(getApplicationContext(),"You have enter more than 30 days",Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            SelectActivity.this);

// Setting Dialog Title
                    alertDialog2.setTitle("Date Error");

// Setting Dialog Message
                    alertDialog2.setMessage("You have enter more than 30 days ");

// Setting Icon to Dialog
                    alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
                    alertDialog2.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
            else
            {
              //  Toast.makeText(getApplicationContext(),"Please Enter Previous Date",Toast.LENGTH_LONG).show();
             //   todate.setError("Please Enter Previous Date");
               fromdate.setText("");
                todate.setText("");
            }

        }
        else
        {
            if(com1==-1&&com2==-1) {
                fromdate.setText("");
                todate.setText("");
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        SelectActivity.this);

// Setting Dialog Title
                alertDialog2.setTitle("Date Error");

// Setting Dialog Message
                alertDialog2.setMessage("You cannot enter future date ");

// Setting Icon to Dialog
                alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.dismiss();
                            }
                        }).show();
              //  todate.setError("Please Enter Previous Date");
             //  fromdate.setText("");
             //   fromdate.setError("Please Enter Previous Date");

            }
            else if(com2==-1)
            { fromdate.setText("");

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        SelectActivity.this);

// Setting Dialog Title
                alertDialog2.setTitle("Date Error");

// Setting Dialog Message
                alertDialog2.setMessage("You cannot enter future Date");

// Setting Icon to Dialog
                alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.dismiss();
                            }
                        }).show();
               // fromdate.setError(null);
               // todate.setError("Please Enter Previous Date");
            }
            else
            {
               // todate.setError(null);
                todate.setText("");
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        SelectActivity.this);

// Setting Dialog Title
                alertDialog2.setTitle("Date Error");

// Setting Dialog Message
                alertDialog2.setMessage("You cannot enter future Date ");

// Setting Icon to Dialog
                alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.dismiss();
                            }
                        }).show();
             //   fromdate.setError("Please Enter Previous Date");
            }
        }


        Log.d("com1 ","  "+com1);
    }
    public  void setDataToSpinner() throws JSONException {
        clinicname=(Spinner)findViewById(R.id.clinic);
        clinicspinner= new ArrayList<>();

        Log.d(TAG,"CLINICS DATA"+CLINICS);


        JSONArray clinic=new JSONArray(CLINICS);
      //  spinnerData=new String[clinic.length()];
        for (int i=0;i<clinic.length();i++)
        {

            JSONObject o=clinic.getJSONObject(i);
            Clinic c= g.fromJson(o.toString(),Clinic.class);
            if(c.isStatus())
            {
                clinicspinner.add(c);
                spinnerData.add(c.getName());

            }

            // Log.d("","yuol"+c.getName()+""+c.getID());




        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,spinnerData);
        // Initializing an ArrayAdapter






       //adapter.setDropDownViewResource(R.layout.spinner_item);

        clinicname.setAdapter(adapter);
        // show hinclinicname.setSelection(adapter.getCount());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        b = getIntent().getExtras();
        String CLINICS = b.get("clinics").toString();
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_revenue:
                if (ConnectivityCheck.haveNetworkConnection(getApplicationContext())) {

                    Intent in = new Intent(this, SelectActivity.class);
                    in.putExtra("clinics", CLINICS);
                    in.putExtra("flag", 3);
                    finish();
                    startActivity(in);

                } else {
                    Toast.makeText(getApplicationContext(), "Please ckeck connectivity", Toast.LENGTH_LONG).show();
                }

                return true;
            case R.id.menu_service:
                if (ConnectivityCheck.haveNetworkConnection(getApplicationContext())) {

                    Intent in = new Intent(this, SelectActivity.class);
                    in.putExtra("clinics", CLINICS);
                    in.putExtra("flag", 1);
                    finish();
                    startActivity(in);

                    //  new MISdata().execute( b.getString("InputJsonString"));
                } else {
                    Toast.makeText(getApplicationContext(), "Please ckeck connectivity", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.menu_patient:
                if (ConnectivityCheck.haveNetworkConnection(getApplicationContext())) {

                    Intent in = new Intent(this, SelectActivity.class);
                    in.putExtra("clinics", CLINICS);
                    in.putExtra("flag", 2);
                    finish();
                    startActivity(in);


                } else {
                    Toast.makeText(getApplicationContext(), "Please ckeck connectivity", Toast.LENGTH_LONG).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getApplicationContext(),spinnerData[position]+"Status"+   clinicspinner.get(position).isStatus()+clinicspinner.get(position).getCode(),Toast.LENGTH_LONG).show();
        selectedClinicCode=clinicspinner.get(position).getCode();
       // status=clinicspinner.get(position).isStatus();
        unitid=clinicspinner.get(position).getUnitID();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public  void goToSelectReportScreen(View v)
    {
        if(fromdate.getText().toString().isEmpty()|| todate.getText().toString().isEmpty())
        {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    SelectActivity.this);

// Setting Dialog Title
            alertDialog2.setTitle("Date Error");

// Setting Dialog Message
            alertDialog2.setMessage("Date should not be empty");

// Setting Icon to Dialog
            alertDialog2.setIcon(R.drawable.delete);

// Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.dismiss();
                        }
                    }).show();
        }
        else {
            searchData();
        }
        input.setClinicCode(selectedClinicCode.trim());
        input.setFromDate(fromdate.getText().toString().trim());
        input.setTodate(todate.getText().toString().trim());
        input.setUnitId(unitid);
        String inputJson=g.toJson(input);
        if(flag==1)
        {
            Log.d("Report DATA","$$$$$$$$$$$"+reportData.isEmpty());
            c.clear();
            list.clear();
            report.clear();
            if(misflag) {
                DateWriterinPdf.fromDate=fromdate.getText().toString();
                DateWriterinPdf.toDate=todate.getText().toString();
                new MISdata().execute(inputJson);
                misflag=false;
            }
            else {

               // fromdate.setError("Please check date");
               // todate.setError("Please check date");
            }
        }
        else if(flag==2)
        {
            registrationPojoArrayList.clear();
            preg.clear();
            if(misflag) {
                DateWriterinPdf.fromDate=fromdate.getText().toString();
                DateWriterinPdf.toDate=todate.getText().toString();
                new MISReg().execute(inputJson);
                misflag=false;
            }
            else {
                //fromdate.setError("Please check date");
                //todate.setError("Please check date");
            }
        }
        else if(flag==3)
        {
            revenuePojoArrayList.clear();
            daily_revenue_finals.clear();
            if(misflag) {
                DateWriterinPdf.fromDate=fromdate.getText().toString();
                DateWriterinPdf.toDate=todate.getText().toString();
                new MISRevenue().execute(inputJson);
                misflag=false;
            }
            else {
                if(fromdate.getText().toString().equals("")) {
                  //  fromdate.setError("Please enter date");
                }
                else {
                   // todate.setError("Please enter date");
                }
            }
        }


    }


    public class MISdata extends AsyncTask {

        boolean tempflag=true;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProDailogs.showDailog(SelectActivity.this);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String dataToPost=objects[0].toString();

            String str=  HTTPclient.PostData(Constants.urlService,dataToPost);
            Log.e("Responce From Server",""+str);
            if(!(str.length()==0))
            {
                Gson g=new Gson();
/*          HashMap <String, ServiceWiseBillingModel> mapper = new HashMap<String, ServiceWiseBillingModel>();
            mapper.put("NS01", new ServiceWiseBillingModel());*/
                try {
                    JSONArray array=new JSONArray(str);
                    if(array.length()==0)
                    {
                        tempflag=false;

                    }
                    ServiceWiseBillingModel nullproblem;
                    for (int i1=0;i1<array.length();i1++)
                    {
                        nullproblem=new ServiceWiseBillingModel();
                        JSONObject obj=array.getJSONObject(i1);
//                        if(obj.getString("Level1").equals(""))
//                        {
//                            nullproblem.setLevel1("others");
//                            Log.d("LEVEL ! NULL",nullproblem.getLevel1());
//                        }
//                        else
//                        {
//                            nullproblem.setLevel1(obj.getString("Level1"));
//                           // Log.e("qw",nullproblem.getLevel1());
//                        }
//                        if(obj.getString("Level2").equals(""))
//                        {
//                            nullproblem.setLevel2("others");
//                            Log.d("LEVEL 2 NULL",nullproblem.getLevel2());
//                        }
//                        else
//                        {
//                            nullproblem.setLevel2(obj.getString("Level2"));
//                            //Log.e("qw",nullproblem.getLevel2());
//                        }
                        nullproblem.setLevel1(obj.getString("Level1"));
                        nullproblem.setLevel2(obj.getString("Level2"));
                        nullproblem.setClinicName(obj.getString("ClinicName"));
                        nullproblem.setNetBillAmount(obj.getDouble("NetBillAmount"));
                        nullproblem.setServiceName(obj.getString("ServiceName"));
                        nullproblem.setPatientName(obj.getString("PatientName"));
                       // ServiceWiseBillingModel m=g.fromJson(obj.toString(),ServiceWiseBillingModel.class);
                        list.add(nullproblem);
                        //Log.d("LEVE!",""+list.get(i1).getLevel1());

                    }
                    for(int i=0;i<list.size();i++)
                    {
                        Log.d("LEVELLL ",""+list.get(i).getLevel1());

                        Myhashset.Level1.add(list.get(i).getLevel1());
                        Myhashset.Level2.add(list.get(i).getLevel2());
                        Myhashset.petiont_name.add(list.get(i).getPatientName());
                        Myhashset.clinicName.add(list.get(i).getClinicName());
                    }

                    //    Log.d("",">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+Myhashset.Services.size()+"pname"+Myhashset.petiont_name.size()+"clin"+Myhashset.clinicName.size());
                    Object[] level1arry=Myhashset.Level1.toArray();

                    Object[] level2arry=Myhashset.Level2.toArray();
                    Object[] clinicarry=Myhashset.clinicName.toArray();
                    int str3count=0;
                    for(int m0=0;m0<clinicarry.length;m0++) {
                        for (int m = 0; m < level1arry.length; m++) {
                            for (int m1 = 0; m1 < list.size(); m1++) {
                                if(((String)clinicarry[m0]).equals(list.get(m1).getClinicName()))
                                {
                                   // Log.e("qwertyuiop","mayur asddffghjk");
                                    String str1 = (String) level1arry[m];



                                    if (str1.equals(list.get(m1).getLevel1())) {
                                        //    Log.d("" + str1, "" + list.get(m1).getLevel2());
                                        for (int m2 = 0; m2 < level2arry.length; m2++) {
                                            String str3 = (String) level2arry[m2];

                                            if (str3.equals(list.get(m1).getLevel2())) {
                                                //        Log.d(str1 + "::" + str3, "" + str3count);
                                                str3count = (int) (str3count + list.get(m1).getNetBillAmount());
                                                c.add(new Finalmodelcalss(str1, str3, list.get(m1).getNetBillAmount(), list.get(m1).getServiceName(), list.get(m1).getPatientName(),list.get(m1).getClinicName()));
                                                break;
                                            }

                                        }

                                        // Log.d("" + str1 + "", "" + list.get(m1).getLevel2()+"\t"+list.get(m1).getClinicName());

                                    }
                                }

                            }
                        }
                        //  Log.d("",""+(String)clinicarry[m0]+"TOTAL AMOUNT"+str3count);
                    }
                    int summ=0;
                    int newServiceCount=0;
                    // Myhashset.Services.clear();
                    Myhashset.petiont_name.clear();
                    for(int m3=0;m3<clinicarry.length;m3++)
                    {
                        for(int m5=0;m5<level1arry.length;m5++)
                        {
                            for(int m6=0;m6<level2arry.length;m6++)
                            {
                                for (int m4=0;m4<c.size();m4++)
                                {

                                    //   Log.d("",""+c.get(m4).toString());
                                    if(c.get(m4).getLevel1().equals((String)level1arry[m5])&&c.get(m4).getLevel2().equals((String)level2arry[m6])&&c.get(m4).getClinicName().equals((String)clinicarry[m3]))
                                    {
                                        summ= (int) (summ+c.get(m4).getNetAmount());
                                        //Myhashset.Services.add(c.get(m4).getServiceName());
                                        Myhashset.petiont_name.add(c.get(m4).getPatientName());
                                        newServiceCount=newServiceCount+1;
                                    }
                                }
                                Log.d(""+level1arry[m5]+" "+level2arry[m6],""+summ+"  Service Count  "+newServiceCount+"  Petioncount "+Myhashset.petiont_name.size()+"\t "+clinicarry[m3]);
                                report.add(new SummuryReport((String) level1arry[m5],(String)level2arry[m6],(String)clinicarry[m3],summ,Myhashset.petiont_name.size(),newServiceCount));
                                summ=0;
                                newServiceCount=0;
                                // Myhashset.Services.clear();
                                Myhashset.petiont_name.clear();

                            }


                        }
                    }

               for (SummuryReport o:report)
                    {

                        if( o.getPetiontCount()==0&&o.getServiceCount()==0)
                        {

                        }
                        else
                        {
                            reportData.add(o);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(tempflag)
            {
                ProDailogs.dismissDailog(getApplicationContext());
                startActivity(new Intent(getApplicationContext(),ServiceWiseBillingReport.class).putExtra("flag",1));
            }
            else {
                ProDailogs.dismissDailog(SelectActivity.this);
                Toast.makeText(getApplicationContext(),"DATA NOT FOUND",Toast.LENGTH_LONG).show();

            }


        }
    }
    public class MISReg extends AsyncTask<String,String,String> {
        boolean tempflag=true;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            ProDailogs.showDailog(SelectActivity.this);
            PatientMaps.ClinicName=new HashSet<>();
            PatientMaps.Regstation_Type=new HashSet<>();
            PatientMaps.Reg_form=new HashSet<>();

        }

        @Override
        protected String doInBackground(String args[]) {
            Gson s=new Gson();
            String dataTOSEnd=args[0];
            String str=  HTTPclient.PostData(Constants.patientService,dataTOSEnd);
            //Log.e("","Patient responce"+str);
            try {
                JSONArray jsonArray=new JSONArray(str);
                if(jsonArray.length()==0)
                {
                    tempflag=false;
                }
                for(int i=0;i<jsonArray.length();i++){


                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    RegistrationPojo p = s.fromJson(jsonObject.toString(), RegistrationPojo.class);
                    //  Log.e("", "Patient " + p.getClinicName());
                    registrationPojoArrayList.add(p);
                    PatientMaps.ClinicName.add(p.getClinicName());
                    PatientMaps.Reg_form.add(p.getRegFrom());
                    PatientMaps.Regstation_Type.add(p.getRegType());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            ProDailogs.dismissDailog(getApplicationContext());
            if(tempflag) {


                Object[] ClinicName = PatientMaps.ClinicName.toArray();
                Object[] Regstation_Type = PatientMaps.Regstation_Type.toArray();
                Object[] Reg_form = PatientMaps.Reg_form.toArray();
                int NetAmt = 0, conunttt = 0;
                for (int i = 0; i < ClinicName.length; i++) {
                    for (int j = 0; j < Regstation_Type.length; j++) {
                        for (int k = 0; k < Reg_form.length; k++) {
                            for (int l = 0; l < registrationPojoArrayList.size(); l++) {
                                conunttt++;
                                if (registrationPojoArrayList.get(l).getClinicName().equals(ClinicName[i]) && registrationPojoArrayList.get(l).getRegType().equals(Regstation_Type[j]) && registrationPojoArrayList.get(l).getRegFrom() == (int) Reg_form[k]) {
                                    NetAmt = NetAmt + 1;
                                }
                            }
                            if (NetAmt == 0) {

                            } else {
                                switch ((int) Reg_form[k]) {
                                    case 0:
//                                    patient_reg_final.setClinicName((String) ClinicName[i]);
//                                    patient_reg_final.setRegType((String) Regstation_Type[j]);
//                                    patient_reg_final.setRegForm("OPD");
//                                    patient_reg_final.setCount(NetAmt);
                                        //Log.d("","OBJECT STRING"+patient_reg_final.toString());
                                        preg.add(new Patient_Reg_Final((String) ClinicName[i], NetAmt, "OPD", (String) Regstation_Type[j]));
                                        Log.d("" + conunttt, "Clinic Nmae :" + ClinicName[i] + "Reg_Type: " + Regstation_Type[j] + "RegForm: " + "OPD " + "Count :" + NetAmt);
                                        break;
                                    case 1:
//                                    patient_reg_final.setClinicName((String) ClinicName[i]);
//                                    patient_reg_final.setRegType((String) Regstation_Type[j]);
//                                    patient_reg_final.setRegForm("IPD");
//                                    patient_reg_final.setCount(NetAmt);
//                                    Log.d("","OBJECT STRING"+patient_reg_final.toString());
                                        preg.add(new Patient_Reg_Final((String) ClinicName[i], NetAmt, "IPD", (String) Regstation_Type[j]));
                                        Log.d("" + conunttt, "Clinic Nmae :" + ClinicName[i] + "Reg_Type: " + Regstation_Type[j] + "RegForm: " + "IPD " + "Count :" + NetAmt);
                                        break;
                                    case 2:
//                                    patient_reg_final.setClinicName((String) ClinicName[i]);
//                                    patient_reg_final.setRegType((String) Regstation_Type[j]);
//                                    patient_reg_final.setRegForm("Pharmacy");
//                                    patient_reg_final.setCount(NetAmt);
//                                    Log.d("","OBJECT STRING"+patient_reg_final.toString());

                                        preg.add(new Patient_Reg_Final((String) ClinicName[i], NetAmt, "Pharmacy", (String) Regstation_Type[j]));
                                        Log.d("" + conunttt, "Clinic Nmae :" + ClinicName[i] + "Reg_Type: " + Regstation_Type[j] + "RegForm: " + "Pharmacy " + "Count :" + NetAmt);
                                        break;
                                    case 5:
//                                    patient_reg_final.setClinicName((String) ClinicName[i]);
//                                    patient_reg_final.setRegType((String) Regstation_Type[j]);
//                                    patient_reg_final.setRegForm("Pathalogy");
//                                    patient_reg_final.setCount(NetAmt);
//                                    Log.d("","OBJECT STRING"+patient_reg_final.toString());

                                        preg.add(new Patient_Reg_Final((String) ClinicName[i], NetAmt, "Pathalogy", (String) Regstation_Type[j]));
                                        Log.d("" + conunttt, "Clinic Nmae :" + ClinicName[i] + "Reg_Type: " + Regstation_Type[j] + "RegForm: " + "Pathalogy " + "Count :" + NetAmt);
                                        break;
                                    default:
//                                    patient_reg_final.setClinicName((String) ClinicName[i]);
//                                    patient_reg_final.setRegType((String) Regstation_Type[j]);
//                                    patient_reg_final.setRegForm("");
//                                    patient_reg_final.setCount(NetAmt);
//                                    Log.d("","OBJECT STRING"+patient_reg_final.toString());

                                        preg.add(new Patient_Reg_Final((String) ClinicName[i], NetAmt, "", (String) Regstation_Type[j]));
                                        Log.d("" + conunttt, "Clinic Nmae :" + ClinicName[i] + "Reg_Type: " + Regstation_Type[j] + "RegForm: " + "" + "Count :" + NetAmt);
                                        break;


                                }
                            }
//                        preg.add(patient_reg_final);

                            // Log.d(""+conunttt,"Clinic Nmae"+ClinicName[i]+"Reg_Type "+Regstation_Type[j]+"RegForm "+Reg_form[k]+"Count "+NetAmt);
                            NetAmt = 0;
                        }

                    }
                    // preg.add(patient_reg_final);
                }
                for (int i = 0; i < preg.size(); i++) {
                    Log.d("", "OBJECT STRING" + preg.get(i).toString());

                }

                startActivity(new Intent(getApplicationContext(), PatientReportActivity.class).putExtra("flag", 2));
            }
            else
            {
                Toast.makeText(getApplicationContext(),"DATA NOT FOUND",Toast.LENGTH_LONG).show();

            }

        }

    }
    public class MISRevenue extends AsyncTask<String,String,String> {
     boolean tempflag=true;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            ProDailogs.showDailog(SelectActivity.this);
            RevenueMap.centerName = new HashSet<>();
            RevenueMap.netBillAmount = new HashSet<>();
            RevenueMap.patientName = new HashSet<>();
            RevenueMap.BillType = new HashSet<>();

             /*pd = new ProgressDialog(ReportActivity.this);
            pd.setMessage("loading");
            pd.show();*/
        }

        @Override
        protected String doInBackground(String args[]) {
            Gson s = new Gson();
            String dataTOSEnd = args[0];
            String str = HTTPclient.PostData(Constants.revenueService, dataTOSEnd);
            //Log.e("","Patient responce"+str);
            try {
                JSONArray jsonArray = new JSONArray(str);
                if(jsonArray.length()==0)
                {
                    tempflag=false;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RevenuePojo p = s.fromJson(jsonObject.toString(), RevenuePojo.class);
                    //  Log.e("", "Patient " + p.getClinicName());
                    revenuePojoArrayList.add(p);
                    RevenueMap.centerName.add(p.getCenterName());
                    RevenueMap.BillType.add(p.getBillType());
                    RevenueMap.netBillAmount.add(p.getNetBillAmount());
                    RevenueMap.patientName.add(p.getPatientName());

                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            ProDailogs.dismissDailog(getApplicationContext());
            if(tempflag) {


                Object[] CenterName = RevenueMap.centerName.toArray();
                Object[] billtype = RevenueMap.BillType.toArray();
                // Object[] netamount=RevenueMap.netBillAmount.toArray();
                Object[] patientname = RevenueMap.patientName.toArray();

                int NetAmt = 0;
                double NetBillAmt = 0;
                for (int i = 0; i < CenterName.length; i++) {
                    for (int j = 0; j < billtype.length; j++) {

                        for (int l = 0; l < revenuePojoArrayList.size(); l++) {

                            if (revenuePojoArrayList.get(l).getCenterName().equals(CenterName[i]) && revenuePojoArrayList.get(l).getBillType() == (int) (billtype[j])) {
                                NetAmt = NetAmt + 1;
                                NetBillAmt = NetBillAmt + revenuePojoArrayList.get(l).getNetBillAmount();
                                // Log.d(""+NetBillAmt,"Clinic Name :"+CenterName[i]+"billType: "+billtype[j]+"netamount: "+netamount[k]);
                            }
                        }
                        if (NetBillAmt == 0) {

                        } else {
                            switch ((int) billtype[j]) {
                                case 0:
                                    daily_revenue_finals.add(new Daily_Revenue_Final("OP", (String) CenterName[i], NetBillAmt, NetAmt));
                                    break;
                                case 1:
                                    daily_revenue_finals.add(new Daily_Revenue_Final("IP", (String) CenterName[i], NetBillAmt, NetAmt));
                                    break;
                                case 2:
                                    daily_revenue_finals.add(new Daily_Revenue_Final("Pharmacy", (String) CenterName[i], NetBillAmt, NetAmt));
                                    break;


                            }

//                        preg.add(patient_reg_final);

                            // Log.d(""+conunttt,"Clinic Nmae"+ClinicName[i]+"Reg_Type "+Regstation_Type[j]+"RegForm "+Reg_form[k]+"Count "+NetAmt);
                            //NetAmt=0;
                            Log.d("" + NetBillAmt, "Clinic Name :" + CenterName[i] + "billType: " + billtype[j] + "netamount: " + NetBillAmt + " Patient Count" + NetAmt);
                            NetAmt = 0;
                            NetBillAmt = 0;
                        }


                    }

                    // preg.add(patient_reg_final);
                }
                for (int i = 0; i < daily_revenue_finals.size(); i++) {
                    Log.d("", "OBJECT STRING" + daily_revenue_finals.get(i).toString());

                }
                startActivity(new Intent(getApplicationContext(), Revenue.class).putExtra("flag", 3));
            }else
            {
                Toast.makeText(getApplicationContext(),"DATA NOT FOUND",Toast.LENGTH_LONG).show();

            }
        }

    }



}
