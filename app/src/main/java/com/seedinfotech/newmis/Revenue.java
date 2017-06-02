package com.seedinfotech.newmis;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import clinicpojo.Daily_Revenue_Final;
import clinicpojo.DateWriterinPdf;
import clinicpojo.NumberFormattingg;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

public class Revenue extends AppCompatActivity {
    ArrayList<Daily_Revenue_Final> graphData;
    Set<String> setClinics;
    Set<String> setL1;
    Object[] setClinicsarray;
    Object[] setL1array;
    BarChart barChart;
    static File myFile;
    boolean screnShotFlag=false;
    int i,j,i1;
    LinearLayout layout;
    //Permision code that will be checked in the method onRequestPermissionsResult
    private int STORAGE_PERMISSION_CODE = 23;
    //Table
    private static  String[][] DATA_TO_SHOW = null;
    ArrayList<String> labels;
    ArrayList<BarEntry>entries;
    ArrayList<String> data;
    MainActivity object;
    int flag;
    ArrayList<Daily_Revenue_Final> reportData=null;
    TableView tableView;
    private Image image1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabularview);
        layout =(LinearLayout)findViewById(R.id.demohgaph);
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

      //  Toast.makeText(this,"FLAg"+flag,Toast.LENGTH_LONG).show();
        graphData=new ArrayList<>();
        graphData=SelectActivity.daily_revenue_finals;

        setClinics = new HashSet<>();
        setL1=new HashSet<>();
        flag=getIntent().getExtras().getInt("flag");
        labels=new ArrayList<>();
        reportData=new ArrayList<>();
        reportData=SelectActivity.daily_revenue_finals;

        object=new MainActivity();

    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            displayGraph(getCurrentFocus());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void Settitle()
    {
        EditText e=(EditText) findViewById(R.id.editText);

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
    }


    public void displayGraph(View v) throws IOException, BadElementException {


        setContentView(R.layout.graphicalview);
        Settitle();
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
        layout =(LinearLayout)findViewById(R.id.demohgaph);

        for(int i=0;i<graphData.size();i++)
        {
            Log.d("old g ",graphData.get(i).toString());
            setClinics.add(graphData.get(i).getClinicName());
            setL1.add(graphData.get(i).getBillCategory());
        }


        ArrayList<String>labels=new ArrayList<>();

        ArrayList<String> l1=new ArrayList<>(setL1);
        int l1Size=l1.size();
        Log.d("Size "," "+l1Size);

        ArrayList<BarEntry>gr1=new ArrayList<>();
        ArrayList<BarEntry>gr2=new ArrayList<>();
        ArrayList<BarEntry>gr3=new ArrayList<>();


        setClinicsarray= setClinics.toArray();
        setL1array= setL1.toArray();
        double sum=0;
        int pos1=0;
        int pos2=0;
        int pos3=0;

      /*  labels.add("ABC");
        labels.add("XYZ");
        labels.add("PQR");*/
        for (int i=0;i<setClinicsarray.length;i++)
        {
            labels.add(setClinicsarray[i].toString());
            for (int j=0;j<setL1array.length;j++)
            {
                for(int k=0;k<graphData.size();k++)
                {
                    if(setClinicsarray[i].equals(graphData.get(k).getClinicName())&&setL1array[j].equals(graphData.get(k).getBillCategory()))
                    {
                        sum=sum+graphData.get(k).getNetAmount();
                    }
                }
                if(setL1array[j].equals("IP"))
                {
                    int sum1= (int) sum;
                    Log.d("IN Ip "," "+setL1array[j]+" "+sum1+" "+pos1);
                    gr1.add(new BarEntry(sum1,pos1));
                    pos1++;
                    Log.d("","CLINIC OPD : " +setClinicsarray[i]+" "+setL1array[j]+"SUM  :"+sum);
                }
                else if(setL1array[j].equals("OP"))
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
                sum=0;

            }





        }

       // barChart.setTouchEnabled(false);
        barChart.setDescription("");

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
                return String.format("%.2f",value)+" Rs.";
            }
        });
        BarDataSet barDataSet1 = new BarDataSet(gr1,"OP");  // creating dataset for group1<br />
        BarDataSet bar2=new BarDataSet(gr2,"IP");
        BarDataSet bar3=new BarDataSet(gr3,"Pharmacy");

        barDataSet1.setColor(Color.parseColor("#00a1e6"));
        bar2.setColor(Color.RED);
        bar3.setColor(Color.parseColor("#4c4c4c"));


        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(bar2);
        dataSets.add(bar3);

        BarData data = new BarData(labels, dataSets);
        barChart.setData(data);
        barChart.setTouchEnabled(true);
        barChart.getViewPortHandler().setMaximumScaleX(1.5f);
        barChart.getViewPortHandler().setMaximumScaleY(2f);
       //barChart.setPinchZoom(true);

    }
   public void generateScrrenshot() throws IOException, BadElementException {
       screnShotFlag=true;
       String mPath = Environment.getExternalStorageDirectory()+ "/" + "Revenue" + ".jpg";
       // create bitmap screen capture
       View v1 = layout;
       v1.setDrawingCacheEnabled(true);
       v1.setDrawingCacheBackgroundColor(Color.WHITE);
       Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());

       File imageFile = new File(mPath);
       FileOutputStream outputStream = new FileOutputStream(imageFile);
       int quality = 100;
       bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
       outputStream.flush();
       outputStream.close();
       image1 = Image.getInstance(mPath);
   }
    public void displayTable(View v)
    {
        try {
            generateScrrenshot();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.tabularview);
        Toolbar t=(Toolbar)findViewById(R.id.toolbar);
       // layout =(LinearLayout)findViewById(R.id.demohgaph);
        setSupportActionBar(t);
        t.setNavigationIcon(R.drawable.ic_drawable_back);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        Settitle();
        tableView = (TableView) findViewById(R.id.tableView);
        tableView.setColumnCount(4);
        Log.d("Size ",reportData.get(0).toString()+"");
        DATA_TO_SHOW=new String[reportData.size()+1][4];
        double  summm=0;
        DecimalFormat formatter = new DecimalFormat("#,###,###");
       // String yourFormattedString = formatter.format(100000);
        for (Daily_Revenue_Final o:reportData)
        {

            Log.d("SUMMERY",""+o.toString());
            for( i=0;i<reportData.size();i++)
            {
                for( j=0;j<4;j++)
                {
                    if(j==0)
                    {
                        DATA_TO_SHOW[i][j]=reportData.get(i).getClinicName();
                    }
                    else if(j==1){
                        DATA_TO_SHOW[i][j]=reportData.get(i).getBillCategory();
                    }
                    else if(j==2){
                        DATA_TO_SHOW[i][j]=String.format("%.2f",reportData.get(i).getNetAmount());
                    }
                    else if(j==3){
                        DATA_TO_SHOW[i][j]=NumberFormattingg.threeDigitFor(reportData.get(i).getPatientCount());
                    }



                }
            }
            summm=summm+o.getNetAmount();
            Log.e("SUMMMMM",""+summm);



           /* if( o.getPetiontCount()==0)
            {

            }
            else
            {
            }*/
        }
        if(i==reportData.size())
        {
            DATA_TO_SHOW[i][1]="Total(Rs.)";
            DATA_TO_SHOW[i][2]=String.format("%.2f",summm);
            Log.e("@@@#@##@#data get", DATA_TO_SHOW[i][2]);
        }

        labels.add("Unit Code");
        labels.add("Bill Type");
        labels.add("Net Amount( Rs.)");
        labels.add("Petient Count");


        //String[] stringArray = getResources().getStringArray(R.array.service_array);

        final String[] stringArray = labels.toArray(new String[0]);


        int colorEvenRows =Color.rgb(255,255,255);
        int colorOddRows = Color.rgb(226,226,226);

        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));

        //  tableView.setColumnModel(columnModel);
        //tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,stringArray));

        SimpleTableHeaderAdapter s=new SimpleTableHeaderAdapter(this,stringArray);

        s.setTextColor(Color.WHITE);

        tableView.setHeaderAdapter(s);
        SimpleTableDataAdapter adpt=new SimpleTableDataAdapter(this, DATA_TO_SHOW);

        tableView.setDataAdapter(adpt);
    }

    public  void generatePdfFile(View v)
    {

        if(isReadStorageAllowed()){
            tableData();
            //If permission is already having then showing the toast
           // Toast.makeText(this,"You already have the permission",Toast.LENGTH_LONG).show();
            //Existing the method with return
            return;
        }

        //If the app has not the permission then asking for the permission
        requestStoragePermission();
    }

    public void tableData()
    {
        myFile = new File(Environment.getExternalStorageDirectory()+"/"+"RevenueReport"+".pdf");
        try {
            if(!myFile.exists())
            {
                myFile.createNewFile();
                createPdf(myFile);
            }
            else
            {
                createPdf(myFile);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
    public void gobackToReports(View v)
    {

        onBackPressed();
        finish();
    }
    public void createPdf(File dest) throws IOException, DocumentException {
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        double pdfsum=0;

        document.open();
       // Paragraph date =new Paragraph("Date"+new Date().getTime());
       // date.setAlignment(Paragraph.ALIGN_RIGHT);
      //  date.setSpacingAfter(20);

        Font bold = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph p1 = new Paragraph("Revenue   Report",bold);
        Paragraph p3date = new Paragraph(""+DilogClasss.dataformat());
        Paragraph fromfateTodate=new Paragraph("From Date:"+ DateWriterinPdf.fromDate+ "    To Date:"+DateWriterinPdf.toDate);
        p1.add(p3date);
        p1.add(fromfateTodate);
        p3date.setAlignment(Element.ALIGN_RIGHT);




        p1.setSpacingAfter(20);

        //add paragraph to document
        document.add(p1);
       // document.add(p3date);


        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Unit Code");
        table.addCell("Bill Type");
        table.addCell("Net Amount (Rs.)");
        table.addCell("Patient count");
        table.setHeaderRows(1);
        table.setWidthPercentage(100);
        table.setTotalWidth(new float[]{ 40*5,100,100,100});
        table.setLockedWidth(true);
        table.setSplitRows(false);
        table.setComplete(false);


        for (int b=0;b<reportData.size();b++)
        {
            for (i1 = 0; i1 < 4; i1++) {
                //  Log.d("",""+reportData.get(b).getClinicName()+ ""+reportData.get(b).getLevel1());
                switch (i1)
                {
                    case 0:  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                        table.addCell( reportData.get(b).getClinicName());
                        break;
                    case 1:  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell( reportData.get(b).getBillCategory());
                        break;
                    case 2:  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell( NumberFormattingg.twoDigitPrecfloat(reportData.get(b).getNetAmount()));
                        pdfsum=pdfsum+reportData.get(b).getNetAmount();
                        break;
                    case 3:  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(""+NumberFormattingg.threeDigitFor(reportData.get(b).getPatientCount()));

                        break;

                }


//        if (i%6 == 0) {
//            document.add(table);
//        }
//        table.addCell("Test " + i);
            }





        }
        for (i1 = 0; i1 < 4; i1++) {
            //  Log.d("",""+reportData.get(b).getClinicName()+ ""+reportData.get(b).getLevel1());
            switch (i1) {
                case 0:
                    table.addCell("");
                    break;
                case 1:
                    table.addCell("Total");
                    break;

                case 2:  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(""+ pdfsum);
                    break;
                case 3:
                    table.addCell("");
                    break;
            }
        }



        table.setComplete(true);
        document.add(table);
        if(screnShotFlag)
        {

        }
        else
        {
            generateScrrenshot();
        }

       /* String mPath = Environment.getExternalStorageDirectory()+ "/" + "Revenue" + ".jpg";
        // create bitmap screen capture
        View v1 = layout;
        v1.setDrawingCacheEnabled(true);
        v1.setDrawingCacheBackgroundColor(Color.WHITE);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        File imageFile = new File(mPath);
        FileOutputStream outputStream = new FileOutputStream(imageFile);
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        outputStream.flush();
        outputStream.close();
        Image image1 = Image.getInstance(mPath);*/
        /*generateScrrenshot();*/
        Paragraph p2 = new Paragraph("Revenue graph",bold);
        p2.setAlignment(Paragraph.ALIGN_LEFT);
        p2.setSpacingBefore(10);
        p2.setSpacingAfter(20);

        //add paragraph to document

        document.add(p2);
        PdfPCell cell1 = new PdfPCell(image1, true);
        PdfPTable t1=new PdfPTable(1);
        t1.setWidthPercentage(100);
        t1.setTotalWidth(new float[]{ 500});
        t1.addCell(cell1);
        document.add(t1);
        document.close();
        startActivity(new Intent(this, MailSendingAcivity.class).putExtra("flag",getIntent().getExtras().getInt("flag")));

     // new UploadFileAsync().execute(myFile.getPath());

    }
    // We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }
    // Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
               // Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
                tableData();
            }else{
                //Displaying another toast if permission is not granted
                //Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }



}
