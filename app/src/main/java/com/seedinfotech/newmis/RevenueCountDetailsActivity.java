package com.seedinfotech.newmis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import clinicpojo.ConnectivityCheck;
import clinicpojo.Daily_Revenue_Final;
import clinicpojo.RevenueDashboardDetailPojo;
import clinicpojo.RevenueDetailspojo;
import de.codecrafters.tableview.TableHeaderAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

public class RevenueCountDetailsActivity extends AppCompatActivity {
TableView tableView;
    private static  String[][] DATA_TO_SHOW = null;
    ArrayList<String> labels;
    ImageView graph;
    ImageView chart;
    Button send,cancl;

    Bundle b;

    int i,j,i1;
    BarChart barChart;
    //ArrayList<Daily_Revenue_Final> graphData;
    Set<String> setClinics;
    Set<String> setL1;
    Object[] setClinicsarray;
    Object[] setL1array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabularview);
        graph=(ImageView)findViewById(R.id.imageView5);
        chart=(ImageView)findViewById(R.id.imageView4);

        send=(Button) findViewById(R.id.button4);
        cancl=(Button) findViewById(R.id.button);
        send.setVisibility(View.INVISIBLE);
        cancl.setVisibility(View.INVISIBLE);


        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t);
        setClinics = new HashSet<>();
        setL1=new HashSet<>();
        t.setNavigationIcon(R.drawable.ic_drawable_back);

        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        displayTable(getCurrentFocus());
    }
    public void displayTable(View v)
    {
        labels=new ArrayList<>();
        setContentView(R.layout.tabularview);
        send=(Button) findViewById(R.id.button4);
        cancl=(Button) findViewById(R.id.button);
        send.setVisibility(View.INVISIBLE);
        cancl.setVisibility(View.INVISIBLE);
        EditText e=(EditText)findViewById(R.id.editText);
        e.setText("Revenue Dashboard");
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

        tableView = (TableView) findViewById(R.id.tableView);
        tableView.setColumnCount(3);
        for (int c=0;c<Dashboard.revenueDetailspojos.size();c++)
        {

        }


        DATA_TO_SHOW=new String[Dashboard.revenueDetailspojos.size()+1][3];
        double  summm=0;
        for (RevenueDetailspojo o:Dashboard.revenueDetailspojos)
        {

            Log.d("SUMMERY",""+o.toString());
            for( i=0;i<Dashboard.revenueDetailspojos.size();i++)
            {
                Log.d("SUMMMM",""+Dashboard.revenueDetailspojos.get(i).getAmt());
                for( j=0;j<3;j++)
                {

                    if(j==0)
                    {
                        DATA_TO_SHOW[i][j]=Dashboard.revenueDetailspojos.get(i).getClinic();
                    }
                    else if(j==1){
                        DATA_TO_SHOW[i][j]=""+Dashboard.revenueDetailspojos.get(i).getBillType();
                    }
                    else if(j==2){
                        DATA_TO_SHOW[i][j]=""+String.format("%.2f ",Dashboard.revenueDetailspojos.get(i).getAmt());


                    }


                }

            }


            summm=summm+o.getAmt();

           /* if( o.getPetiontCount()==0)
            {

            }
            else
            {
            }*/
        }
        if(i==Dashboard.revenueDetailspojos.size())
        {
            DATA_TO_SHOW[i][1]="Total";
            DATA_TO_SHOW[i][2]=String.format("%.2f",summm);
            Log.e("@@@#@##@#data get", DATA_TO_SHOW[i][2]);
        }

        labels.add("Unit Code");
        labels.add("Bill Type");
        labels.add("Net Amount( Rs.)");

       final String[] stringArray = getResources().getStringArray(R.array.service_array);

        //labels.toArray(new String[0]);

        int colorEvenRows =Color.rgb(255,255,255);
        int colorOddRows = Color.rgb(226,226,226);

        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));




        //  tableView.setColumnModel(columnModel);
        //tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,stringArray));
        SimpleTableHeaderAdapter s=new SimpleTableHeaderAdapter(this,stringArray);
        s.setTextColor(Color.WHITE);

        tableView.setHeaderAdapter(s);



        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
    }
    public void displayGraph(View v)
    {

        // layout=(LinearLayout)findViewById(R.id.demohgaph);
        setContentView(R.layout.graphicalview);
        send=(Button) findViewById(R.id.button4);
        cancl=(Button) findViewById(R.id.button);
        send.setVisibility(View.INVISIBLE);
        cancl.setVisibility(View.INVISIBLE);
        EditText e1=(EditText)findViewById(R.id.editText);
        e1.setText("Revenue Dashboard");

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
        barChart = (BarChart) findViewById(R.id.demo);

        for (RevenueDetailspojo o:Dashboard.revenueDetailspojos)
        {

            Log.d("SUMMERY",""+o.toString());
            for( i=0;i<Dashboard.revenueDetailspojos.size();i++)
            {
                Log.d("SUMMMM",""+Dashboard.revenueDetailspojos.get(i).getAmt());
                for( j=0;j<3;j++)
                {

                    if(j==0)
                    {
                        //DATA_TO_SHOW[i][j]=Dashboard.revenueDetailspojos.get(i).getClinic();
                        setClinics.add(Dashboard.revenueDetailspojos.get(i).getClinic());
                    }
                    else if(j==1){
                        //DATA_TO_SHOW[i][j]=""+Dashboard.revenueDetailspojos.get(i).getBillType();
                        setL1.add(Dashboard.revenueDetailspojos.get(i).getBillType());
                    }
                    else if(j==2){
                       // DATA_TO_SHOW[i][j]=""+Dashboard.revenueDetailspojos.get(i).getAmt();

                    }


                }

            }
        }

      /*  for(int i=0;i<graphData.size();i++)
        {
            Log.d("old g ",graphData.get(i).toString());
            setClinics.add(graphData.get(i).getClinicName());
            setL1.add(graphData.get(i).getBillCategory());
        }*/


        ArrayList<String>labels=new ArrayList<>();

        ArrayList<String>l1=new ArrayList<>(setL1);
        int l1Size=l1.size();
        Log.d("Size "," "+l1Size);

        ArrayList<BarEntry>gr1=new ArrayList<>();
        ArrayList<BarEntry>gr2=new ArrayList<>();
        ArrayList<BarEntry>gr3=new ArrayList<>();
        ArrayList<BarEntry>gr4=new ArrayList<>();


        setClinicsarray= setClinics.toArray();
        setL1array= setL1.toArray();
        double sum=0;
        int pos1=0;
        int pos2=0;
        int pos3=0;
        int pos4=0;

      /*  labels.add("ABC");
        labels.add("XYZ");
        labels.add("PQR");*/
        for (int i=0;i<setClinicsarray.length;i++)
        {
            labels.add(setClinicsarray[i].toString());
            for (int j=0;j<setL1array.length;j++)
            {
                for(int k=0;k<Dashboard.revenueDetailspojos.size();k++)
                {
                    if(setClinicsarray[i].equals(Dashboard.revenueDetailspojos.get(k).getClinic())&&setL1array[j].equals(Dashboard.revenueDetailspojos.get(k).getBillType()))
                    {
                        sum=sum+Dashboard.revenueDetailspojos.get(k).getAmt();
                    }
                }
                if(setL1array[j].equals("IPD"))
                {
                    int sum1= (int) sum;
                    Log.d("IN Ip "," "+setL1array[j]+" "+sum1+" "+pos1);
                    gr1.add(new BarEntry(sum1,pos1));
                    pos1++;
                    Log.d("","CLINIC OPD : " +setClinicsarray[i]+" "+setL1array[j]+"SUM  :"+sum);
                }
                else if(setL1array[j].equals("OPD"))
                {
                    int sum1= (int) sum;
                    Log.d("IN op "," "+setL1array[j]+" "+sum1+" "+pos2);
                    gr2.add(new BarEntry(sum1,pos2));
                    Log.d("","CLINIC IPD : " +setClinicsarray[i]+" "+setL1array[j]+"SUM  :"+sum);


                    pos2++;
                }
                else if(setL1array[j].equals("Pharmacy"))
                {
                    int sum1= (int) sum;
                    Log.d("IN op "," "+setL1array[j]+" "+sum1+" "+pos3);
                    gr3.add(new BarEntry(sum1,pos3));
                    Log.d("","CLINIC Pharamacy : " +setClinicsarray[i]+" "+setL1array[j]+"SUM  :"+sum);


                    pos3++;
                }
                else if(setL1array[j].equals("Other"))
                {
                    int sum1= (int) sum;
                    Log.d("IN op "," "+setL1array[j]+" "+sum1+" "+pos3);
                    gr3.add(new BarEntry(sum1,pos3));
                    Log.d("","CLINIC Pharamacy : " +setClinicsarray[i]+" "+setL1array[j]+"SUM  :"+sum);


                    pos4++;
                }
                sum=0;

            }





        }
        barChart.setDescription("");
        barChart.setTouchEnabled(true);
        barChart.getViewPortHandler().setMaximumScaleX(1.5f);
        barChart.getViewPortHandler().setMaximumScaleY(2f);

        YAxis left = barChart.getAxisLeft();
        YAxis right=barChart.getAxisRight();
        right.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return "";
            }
        });
        left.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return  String.format("%.2f",value)+" Rs.";
            }
        });
        BarDataSet barDataSet1 = new BarDataSet(gr1,"IPD");  // creating dataset for group1<br />
        BarDataSet bar2=new BarDataSet(gr2,"OPD");
        BarDataSet bar3=new BarDataSet(gr3,"Pharmacy");
        BarDataSet bar4=new BarDataSet(gr4,"Other");

        barDataSet1.setColor(Color.GREEN);
        bar2.setColor(Color.RED);
        bar3.setColor(Color.BLUE);
        bar4.setColor(Color.RED);


        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(bar2);
        dataSets.add(bar3);
        dataSets.add(bar4);

        BarData data = new BarData(labels, dataSets);
        barChart.setData(data);

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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
