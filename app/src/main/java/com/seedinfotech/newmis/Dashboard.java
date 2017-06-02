package com.seedinfotech.newmis;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import clinicpojo.ClinicWiseRevenue;
import commonmodules.ProDailogs;
import clinicpojo.ConnectivityCheck;
import clinicpojo.Constants;
import clinicpojo.PataintCountDashBoard;
import clinicpojo.RevenueDetailspojo;
import request.handelers.HTTPclient;

public class Dashboard extends AppCompatActivity {
    PieChart pieChart;
    int opdSuum = 0;
    String Desiganation;
    static ArrayList<PataintCountDashBoard> summury;
    JSONArray desg;
    int ipdSum = 0;
    //ProgressDialog pd;
    static boolean flag = true;
    int pharmacySum = 0;
    int otherSum = 0;
    String CLinics;
    JSONObject UNITID;
    Toolbar toolbar;

    Button b1;
    Bundle b;
    Button r2;
    static  ArrayList<ClinicWiseRevenue> clinicWiseRevenue;
    static ArrayList<RevenueDetailspojo> revenueDetailspojos;
    //for navigation drawer
    private DrawerLayout drawerLayout;
    NavigationView n;
    Intent intent;
    String username;
    TextView profilename;

   // static String revenueDashboardURL = "http://14.141.60.217/revenue_dashboard";
    //static String patientDashboardURL = "http://14.141.60.217/newpetientcountdash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
       // summury=new ArrayList<>();
      //  revenueDetailspojos = new ArrayList<>();
       // clinicWiseRevenue=new ArrayList<>();
        r2 = (Button) findViewById(R.id.button8);
        b1 = (Button) findViewById(R.id.button9);
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(false);
        pieChart.setFocusable(false);
        CLinics = getIntent().getStringExtra("clinics");
        username = getIntent().getStringExtra("username");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.e("Dashboard oncreate resp", "" + CLinics);
        drowerlayout();

    }

    @Override
    protected void onResume() {
        super.onResume();
        dispalyRevenueCountdash(getCurrentFocus());
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
                    startActivity(in);

                } else {
                    Toast.makeText(getApplicationContext(), "Plase check connectivity", Toast.LENGTH_LONG).show();
                }

                return true;
            case R.id.menu_service:
                if (ConnectivityCheck.haveNetworkConnection(getApplicationContext())) {

                    Intent in = new Intent(this, SelectActivity.class);
                    in.putExtra("clinics", CLINICS);
                    in.putExtra("flag", 1);
                    startActivity(in);

                    //  new MISdata().execute( b.getString("InputJsonString"));
                } else {
                    Toast.makeText(getApplicationContext(), "Plase check connectivity", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.menu_patient:
                if (ConnectivityCheck.haveNetworkConnection(getApplicationContext())) {

                    Intent in = new Intent(this, SelectActivity.class);
                    in.putExtra("clinics", CLINICS);
                    in.putExtra("flag", 2);
                    startActivity(in);


                } else {
                    Toast.makeText(getApplicationContext(), "Plase ckeck connectivity", Toast.LENGTH_LONG).show();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void drowerlayout() {
        /*for navigation drawer*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_hamburger);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(Gravity.LEFT);
                profilename = (TextView) findViewById(R.id.username);
                profilename.setText(username);
                try {
                    desg=new JSONArray(CLinics);
                    JSONObject desgn=desg.getJSONObject(0);
                    Desiganation=  desgn.getString("Description");
                    TextView DesignationName= (TextView)findViewById(R.id.designation);
                    DesignationName.setText(Desiganation.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        n = (NavigationView) findViewById(R.id.nav_view);


        //fragment = new HomeScreenFragment();
        // android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.fr_frame, fragment, "HomeScreen");
        //ft.commit();

        n.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                       /* intent = new Intent(getApplicationContext(), .class);
                        startActivity(intent);*/
                     //   Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_report:
                        intent = new Intent(getApplicationContext(), ReportActivity.class);
                        intent.putExtra("clinics", getIntent().getStringExtra("clinics"));
                        startActivity(intent);
                       // Toast.makeText(getApplicationContext(), "Report", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_logdetail:

                       /* intent = new Intent(getApplicationContext(), AboutUsActivity.class);
                        startActivity(intent);*/
                       // Toast.makeText(getApplicationContext(), "Log Details", Toast.LENGTH_LONG).show();

                        break;
                    case R.id.nav_logout:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        summury=null;
                        revenueDetailspojos=null;
                        clinicWiseRevenue=null;
                        finishAffinity();
                        startActivity(intent);


                      //  Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                        break;
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

    }

    public void patientDashboardDeatil(View v) {
        if (ConnectivityCheck.haveNetworkConnection(getApplicationContext())) {
            startActivity(new Intent(this, RevenueCountDetailsActivity.class).putExtra("clinics", getIntent().getStringExtra("clinics")));
        } else {
            Toast.makeText(getApplicationContext(), " Plase check Connectivity", Toast.LENGTH_LONG).show();
        }

    }

    public void dispalyPationCountdash(View v)

    {
        Button button = (Button) findViewById(R.id.button10);
        button.setVisibility(View.GONE);
        pieChart.setRotationEnabled(false);
        pieChart.setFocusable(false);

        flag = true;
        if (flag == true) {
            b1.setBackgroundColor(Color.WHITE);
            r2.setBackgroundResource(R.drawable.border);
            b1.setTextColor(Color.parseColor("#ED6140"));
            r2.setTextColor(Color.WHITE);

        } else {
            b1.setBackgroundResource(R.drawable.border);
        }


        if (ConnectivityCheck.haveNetworkConnection(getApplicationContext())) {
            if(summury==null)
            {
                new patientCountDashboard().execute();
            }
            else
            {
                ArrayList<String> xVals = new ArrayList<String>();

                ArrayList<Entry> yvalues = new ArrayList<Entry>();
                for (int m = 0; m < summury.size(); m++) {
                    yvalues.add(new Entry(summury.get(m).getPatientCount(), m));
                    xVals.add(summury.get(m).getLevel4());

                }


                PieDataSet dataSet = new PieDataSet(yvalues, "Count");

                pieChart.setDrawHoleEnabled(false);
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);

                PieData data = new PieData(xVals, dataSet);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(12f);
                pieChart.setDescription("Patient count Report");

                pieChart.setData(data);
                data.notifyDataChanged();
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
            }



        } else {
            Toast.makeText(getApplicationContext(), " Plase check Connectivity", Toast.LENGTH_LONG).show();
        }

    }

    public void dispalyRevenueCountdash(View v) {
        Button btn = (Button) findViewById(R.id.button10);
        btn.setVisibility(View.VISIBLE);
        pieChart.setRotationEnabled(false);
        pieChart.setFocusable(false);
        flag = false;
        if (flag == false) {
            b1.setTextColor(Color.WHITE);
            r2.setBackgroundColor(Color.WHITE);
            r2.setTextColor(Color.parseColor("#ED6140"));
            b1.setBackgroundResource(R.drawable.border);

        } else {

            r2.setBackgroundResource(R.drawable.border);
        }
        if (ConnectivityCheck.haveNetworkConnection(getApplicationContext())) {
            if(revenueDetailspojos==null&&clinicWiseRevenue==null)
            {
                new revenueDashboardRreport().execute();
            } else
            {
                ArrayList<String> xVals = new ArrayList<String>();
                ArrayList<Entry> yvalues = new ArrayList<Entry>();
                Log.d("FINAL", revenueDetailspojos.toString());
                Log.d("SUMS", "OPDSUM " + opdSuum + "IPDSUM " + ipdSum + "PharmacySUM" + pharmacySum + "OtherSum " + otherSum);


                for (int i=0;i<clinicWiseRevenue.size();i++) {
                    yvalues.add(new Entry((float) clinicWiseRevenue.get(i).getRevenueSum(), i));
                    xVals.add(clinicWiseRevenue.get(i).getUnitCode());
                }

                PieDataSet dataSet = new PieDataSet(yvalues, "Revenue");

                pieChart.setDrawHoleEnabled(false);
                dataSet.setColors(ColorTemplate.PASTEL_COLORS);

                PieData data = new PieData(xVals, dataSet);
                data.setValueTextSize(12f);
                data.setValueFormatter(new PercentFormatter());
                pieChart.setDescription("Revenue Report");
                pieChart.setData(data);
                data.notifyDataChanged();
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();

            }


        } else {
            Toast.makeText(getApplicationContext(), " Please check Connectivity", Toast.LENGTH_LONG).show();
        }

    }



    public class revenueDashboardRreport extends AsyncTask<String, String, String> {
        JSONObject obj;

        double ClinicWiseSumRevenueIPD = 0;
        double ClinicWiseSumRevenueOPD = 0;
        double ClinicWiseSumRevenueOTHER = 0;
        double ClinicWiseSumRevenuePharmacy = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           ProDailogs.showDailog(Dashboard.this);
            revenueDetailspojos = new ArrayList<>();
            clinicWiseRevenue=new ArrayList<>();
            Date d = new Date();
            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            String inputedate = sdf.format(d);
            obj = new JSONObject();
            try {
                obj.put("currentDate", "2016-07-07");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject obj1;
            String resp = HTTPclient.PostData(Constants.revenueDashboardURL, obj.toString());
            int cilinicWiseSum=0;

            Log.e("Dashboard resp", "" + UNITID);
            try {
                JSONArray a = new JSONArray(resp);
                JSONArray clinic = new JSONArray(CLinics);
                for (int i = 0; i < clinic.length(); i++) {
                    for (int b = 0; b < a.length(); b++) {
                        UNITID = clinic.getJSONObject(i);
                        obj1 = a.getJSONObject(b);

                        if ((UNITID.getString("UnitID").equals(obj1.getString("BillUnitID"))) && UNITID.getBoolean("Status")) {
                            cilinicWiseSum=  cilinicWiseSum + obj1.getInt("NetBillAmount");

                            switch (obj1.getInt("BillType")) {
                                case 0:
                                    opdSuum = opdSuum + obj1.getInt("NetBillAmount");
                                    ClinicWiseSumRevenueOPD = ClinicWiseSumRevenueOPD + obj1.getInt("NetBillAmount");
                                    break;
                                case 1:
                                    ipdSum = ipdSum + obj1.getInt("NetBillAmount");
                                    ClinicWiseSumRevenueIPD = ClinicWiseSumRevenueIPD + obj1.getInt("NetBillAmount");
                                    break;
                                case 2:
                                    pharmacySum = pharmacySum + obj1.getInt("NetBillAmount");
                                    ClinicWiseSumRevenuePharmacy = ClinicWiseSumRevenuePharmacy + obj1.getInt("NetBillAmount");
                                    break;
                                case 4:
                                    otherSum = otherSum + obj1.getInt("NetBillAmount");
                                    ClinicWiseSumRevenueOTHER = ClinicWiseSumRevenueOTHER + obj1.getInt("NetBillAmount");
                                    break;

                            }
                        }

                    }
                    if(cilinicWiseSum!=0){
                        clinicWiseRevenue.add(new ClinicWiseRevenue(cilinicWiseSum,UNITID.getString("Code")));

                    }
                    Log.d("CINIC WISE SUM","CLINIC "+UNITID.getString("Code")+""+cilinicWiseSum);
                    cilinicWiseSum=0;
                    if (ClinicWiseSumRevenueIPD > 0) {
                        revenueDetailspojos.add(new RevenueDetailspojo(UNITID.getString("Code"), ClinicWiseSumRevenueIPD, "IPD"));
                    }
                    if (ClinicWiseSumRevenueOPD > 0) {
                        revenueDetailspojos.add(new RevenueDetailspojo(UNITID.getString("Code"), ClinicWiseSumRevenueOPD, "OPD"));

                    }
                    if (ClinicWiseSumRevenueOTHER > 0) {
                        revenueDetailspojos.add(new RevenueDetailspojo(UNITID.getString("Code"), ClinicWiseSumRevenueOTHER, "Other"));

                    }
                    if (ClinicWiseSumRevenuePharmacy > 0) {
                        revenueDetailspojos.add(new RevenueDetailspojo(UNITID.getString("Code"), ClinicWiseSumRevenuePharmacy, "Pharmacy"));

                    }
//                    revenueDetailspojos.add(new RevenueDetailspojo(UNITID.getString("Code"),ClinicWiseSumRevenueIPD,ClinicWiseSumRevenueOTHER,ClinicWiseSumRevenueOPD,ClinicWiseSumRevenuePharmacy));
                    ClinicWiseSumRevenueIPD = 0;
                    ClinicWiseSumRevenueOPD = 0;
                    ClinicWiseSumRevenueOTHER = 0;
                    ClinicWiseSumRevenuePharmacy = 0;


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ProDailogs.dismissDailog(getApplicationContext());
            ArrayList<String> xVals = new ArrayList<String>();
            ArrayList<Entry> yvalues = new ArrayList<Entry>();
            Log.d("FINAL", revenueDetailspojos.toString());
            Log.d("SUMS", "OPDSUM " + opdSuum + "IPDSUM " + ipdSum + "PharmacySUM" + pharmacySum + "OtherSum " + otherSum);


            for (int i=0;i<clinicWiseRevenue.size();i++) {
                yvalues.add(new Entry((float) clinicWiseRevenue.get(i).getRevenueSum(), i));
                xVals.add(clinicWiseRevenue.get(i).getUnitCode());
            }

            PieDataSet dataSet = new PieDataSet(yvalues, "Revenue");

            pieChart.setDrawHoleEnabled(false);
            dataSet.setColors(ColorTemplate.PASTEL_COLORS);

            PieData data = new PieData(xVals, dataSet);
            data.setValueTextSize(12f);
            data.setValueFormatter(new PercentFormatter());
            pieChart.setDescription("Revenue Report");
            pieChart.setData(data);
            data.notifyDataChanged();
            pieChart.notifyDataSetChanged();
            pieChart.invalidate();

        }
    }

    public class patientCountDashboard extends AsyncTask<String, String, String> {
        JSONObject obj;
        HashSet<String> str;


        Object level4arry[];

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProDailogs.showDailog(Dashboard.this);

            Date d = new Date();
            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            String inputedate = sdf.format(d);
            obj = new JSONObject();
            try {
                obj.put("currentDate", "2016-07-07");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            str = new HashSet<>();
            summury = new ArrayList<>();

        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject obj1;
            String resp = HTTPclient.PostData(Constants.patientDashboardURL, obj.toString());


            int Count = 0;
            Log.e("Dashboard resp", "" + UNITID);
            try {
                JSONArray a = new JSONArray(resp);

                JSONArray clinic = new JSONArray(CLinics);
                for (int j = 0; j < a.length(); j++) {
                    str.add(a.getJSONObject(j).getString("Level2"));

                }
                level4arry = str.toArray();


                for (int k = 0; k < level4arry.length; k++) {
                    for (int i = 0; i < clinic.length(); i++) {
                        for (int b = 0; b < a.length(); b++) {


                            UNITID = clinic.getJSONObject(i);
                            obj1 = a.getJSONObject(b);
                            if ((UNITID.getString("Code").equals(obj1.getString("ClinicName"))) && UNITID.getBoolean("Status")) {

                                if (((String) level4arry[k]).equals(obj1.getString("Level2"))) {
                                    Count = Count + 1;
                                }


                            }

                        }


                    }
                    Log.d("Level2", (String) level4arry[k] + "\t" + Count);
                    if (Count != 0) {
                        summury.add(new PataintCountDashBoard((String) level4arry[k], Count));

                    }

                    Count = 0;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return summury.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ProDailogs.dismissDailog(getApplicationContext());
            Log.d("bnfghfghfhf", "SUMMURY" + s);


            ArrayList<String> xVals = new ArrayList<String>();

            ArrayList<Entry> yvalues = new ArrayList<Entry>();
            for (int m = 0; m < summury.size(); m++) {
                yvalues.add(new Entry(summury.get(m).getPatientCount(), m));
                xVals.add(summury.get(m).getLevel4());

            }


            PieDataSet dataSet = new PieDataSet(yvalues, "Count");

            pieChart.setDrawHoleEnabled(false);
            dataSet.setColors(ColorTemplate.PASTEL_COLORS);

            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(12f);
            pieChart.setDescription("Patient count Report");

            pieChart.setData(data);
            data.notifyDataChanged();
            pieChart.notifyDataSetChanged();
            pieChart.invalidate();



        }
    }
}
