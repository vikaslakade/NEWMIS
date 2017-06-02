package com.seedinfotech.newmis;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
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

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;


import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import clinicpojo.DateWriterinPdf;
import clinicpojo.NumberFormattingg;
import clinicpojo.SummuryReport;
import de.codecrafters.tableview.TableHeaderAdapter;
import de.codecrafters.tableview.TableView;

import de.codecrafters.tableview.model.TableColumnPxWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;
import fileupload.ApiConfig;
import fileupload.AppConfig;
import fileupload.ServerResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceWiseBillingReport extends AppCompatActivity  {
TextView tableDecreption;
    ArrayList<SummuryReport> graphData;  //for graph
    Set<String> setClinics;
    Set<String> setL1;
    Object[] setClinicsarray;
    Object[] setL1array;
    BarChart barChart;

    Uri path;
    static File myFile;
    int i,j,i1;
    LinearLayout layout;
    //Permision code that will be checked in the method onRequestPermissionsResult
    private int STORAGE_PERMISSION_CODE = 23;
    //Table
    String[][] DATA_TO_SHOW = null;


    ArrayList<String> labels;
    ArrayList<BarEntry>entries;
    ArrayList<String> data;
    MainActivity object;
    ArrayList<SummuryReport> reportData=null;
    TableView tableView;
    int flag;
    private Image image1;
    private boolean screnShotFlag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tabularview);
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
        initdata();
        flag=getIntent().getExtras().getInt("flag");
       // Toast.makeText(this,"FLAg"+flag,Toast.LENGTH_LONG).show();
        object=new MainActivity();
        displayGraph(getCurrentFocus());
        reportData=new ArrayList<>();
        reportData=SelectActivity.reportData;

    }


public void initdata(){
    setClinics = new HashSet<>();
    setL1=new HashSet<>();

    data=new ArrayList<>();
    labels=new ArrayList<>();
}
    public void findById(){

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

    public void displayGraph(View v)
    {
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


        layout=(LinearLayout)findViewById(R.id.demohgaph); //for scrreenshot

        graphData=new ArrayList<>();
        graphData=SelectActivity.reportData;

        barChart.setVisibility(View.VISIBLE);

        for(int i=0;i<graphData.size();i++)
        {
            Log.d("old g ",graphData.get(i).toString());
            setClinics.add(graphData.get(i).getClinicName());
            setL1.add(graphData.get(i).getLevel1());
        }


        ArrayList<String> labels=new ArrayList<>();

        ArrayList<String> l1=new ArrayList<>(setL1);
        int l1Size=l1.size();
        Log.d("Size "," "+l1Size);

        ArrayList<BarEntry>gr1=new ArrayList<>();
        ArrayList<BarEntry>gr2=new ArrayList<>();

        setClinicsarray= setClinics.toArray();
        setL1array= setL1.toArray();
        double sum=0;
        int pos1=0;
        int pos2=0;



        for (int i=0;i<setClinicsarray.length;i++)
        {
            labels.add(setClinicsarray[i].toString());
            for (int j=0;j<setL1array.length;j++)
            {
                for(int k=0;k<graphData.size();k++)
                {
                    if(setClinicsarray[i].equals(graphData.get(k).getClinicName())&&setL1array[j].equals(graphData.get(k).getLevel1()))
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
                    Log.d("","CLINIC IP : " +setClinicsarray[i]+" "+setL1array[j]+"SUM  :"+sum);
                }
                else if(setL1array[j].equals("OP"))
                {
                    int sum1= (int) sum;
                    Log.d("IN op "," "+setL1array[j]+" "+sum1+" "+pos2);
                    gr2.add(new BarEntry(sum1,pos2));
                    Log.d("","CLINIC op : " +setClinicsarray[i]+" "+setL1array[j]+"SUM  :"+sum);


                    pos2++;
                }

//               Log.d("","CLINIC : " +setClinicsarray[i]+" "+setL1array[j]+"SUM  :"+sum);
                sum=0;

            }





        }

        //barChart.setTouchEnabled(false);
        barChart.setTouchEnabled(true);
        barChart.getViewPortHandler().setMaximumScaleX(1.5f);
        barChart.getViewPortHandler().setMaximumScaleY(2f);
        barChart.setDescription("");


        BarDataSet barDataSet1 = new BarDataSet(gr1,"IP");  // creating dataset for group1<br />
        BarDataSet bar2=new BarDataSet(gr2,"OP");
        barDataSet1.setColor(Color.parseColor("#ed1b24"));
        bar2.setColor(Color.parseColor("#00a1e6"));
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(bar2);
        BarData data = new BarData(labels, dataSets);
        barChart.setData(data);

        Log.d("CN ",setClinics.toString());
        XAxis x= barChart.getXAxis();
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
              return NumberFormattingg.twoDigitPrecfloat( value)+" Rs.";
          }
      });


        x.setLabelsToSkip(0);
        Log.d("","LONGEXT LABLE");
    }


    public void generateScrrenshot() throws IOException, BadElementException {
        screnShotFlag=true;
        String mPath = Environment.getExternalStorageDirectory()+ "/" + "ServiceReport" + ".jpg";
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


        tableDecreption=(TextView)findViewById(R.id.descreption);
        tableDecreption.setText("  PC:Patient Count, SC :Service Count, L1:Level1, L2:Level2 ");
        tableView = (TableView) findViewById(R.id.tableView);
        tableView.setColumnCount(6);


        tableView.setVisibility(View.VISIBLE);

        Log.d("Size ",SelectActivity.reportData.size()+"");
        DATA_TO_SHOW=new String[reportData.size()+1][6];
        double  summm=0;
        DecimalFormat formatter = new DecimalFormat("##,##,###");
        NumberFormat nf=new DecimalFormat("##00");
        //String FormattedString = formatter.format(100000);
        for (SummuryReport o:reportData)
        {
            Log.d("SUMMERY",""+o.toString());
            for( i=0;i<reportData.size();i++)
            {
                for( j=0;j<6;j++)
                {
                    if(j==0)
                    {
                        DATA_TO_SHOW[i][j]=reportData.get(i).getClinicName();
                    }
                    else if(j==1){
                        DATA_TO_SHOW[i][j]=reportData.get(i).getLevel1();
                    }
                    else if(j==2){
                        DATA_TO_SHOW[i][j]=reportData.get(i).getLevel2();
                    }
                    else if(j==3){
                        DATA_TO_SHOW[i][j]=nf.format(reportData.get(i).getServiceCount());
                    }
                    else if(j==4){
                        DATA_TO_SHOW[i][j]=nf.format(reportData.get(i).getPetiontCount());
                    }
                    else if(j==5){
                        DATA_TO_SHOW[i][j]=String.format("%.2f",reportData.get(i).getNetAmount());

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
            DATA_TO_SHOW[i][4]="Total (Rs.)";
            DATA_TO_SHOW[i][5]=String.format("%.2f",summm);
            Log.e("@@@#@##@#data get", DATA_TO_SHOW[i][5]);
        }


        labels.add("Unit");
        labels.add("L1");
        labels.add("L2");
        labels.add("SC");
        labels.add("PC");
        labels.add("Net Amount(Rs.)");

      final  String[] stringArray = labels.toArray(new String[0]);

      TableColumnWeightModel columnModel = new TableColumnWeightModel(6);
        columnModel.setColumnWeight(0,100);
        columnModel.setColumnWeight(1,100);
        columnModel.setColumnWeight(2,150);
        columnModel.setColumnWeight(3,100);
        columnModel.setColumnWeight(4,100);
        columnModel.setColumnWeight(5,250);


        tableView.setColumnModel(columnModel);


        int colorEvenRows =Color.rgb(255,255,255);
        int colorOddRows = Color.rgb(226,226,226);

        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));

        SimpleTableHeaderAdapter s=new SimpleTableHeaderAdapter(this,stringArray);
        s.setTextColor(Color.WHITE);

        tableView.setHeaderAdapter(s);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));

    }
     public void gobackToReports(View v)
    {

      onBackPressed();
        finish();
    }
        public  void generatePdfFile(View v)
        {
            tableData();
            if(isReadStorageAllowed()){
                //If permission is already having then showing the toast
              //  Toast.makeText(this,"You already have the permission",Toast.LENGTH_LONG).show();
                //Existing the method with return
                return;
            }

            //If the app has not the permission then asking for the permission
            requestStoragePermission();
        }

            public void tableData()
            {
                myFile = new File(Environment.getExternalStorageDirectory()+"/"+"ServiceWiseReport"+".pdf");
                path= Uri.parse(myFile.getPath());
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
    public void createPdf(File dest) throws IOException, DocumentException {
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        double pdfsum=0;

        document.open();
        Font bold = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph p1 = new Paragraph("Service Wise  Billing Report",bold);
        Paragraph fromfateTodate=new Paragraph("From Date:"+ DateWriterinPdf.fromDate+ "    To Date:"+DateWriterinPdf.toDate);
        Paragraph p3date = new Paragraph(""+DilogClasss.dataformat());
        p3date.setAlignment(Paragraph.ALIGN_RIGHT);
        p1.setAlignment(Paragraph.ALIGN_LEFT);
        p1.add(p3date);
        p1.add(fromfateTodate);
        p1.setSpacingAfter(20);


        //add paragraph to document
        document.add(p1);


        PdfPTable table = new PdfPTable(6);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Clinic Name");
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell("Level 1");
        table.addCell("Level 2");
        table.addCell("Service Count");
        table.addCell("Patient Count");
        table.addCell("Net Amount (Rs.)");
        table.setHeaderRows(1);
        table.setWidthPercentage(100);
        table.setTotalWidth(new float[]{ 40*4,90,90,70,70,80});
        table.setLockedWidth(true);
        table.setSplitRows(false);
        table.setComplete(false);


        for (int b=0;b<reportData.size();b++)
        {
            for (i1 = 0; i1 < 6; i1++) {
              //  Log.d("",""+reportData.get(b).getClinicName()+ ""+reportData.get(b).getLevel1());
                switch (i1)
                {
                    case 0:table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(reportData.get(b).getClinicName());
                        break;
                    case 1:table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell( reportData.get(b).getLevel1());
                        break;
                    case 2:table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell( reportData.get(b).getLevel2());
                        break;
                    case 3:  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(""+ NumberFormattingg.threeDigitFor(reportData.get(b).getServiceCount()));

                        break;
                    case 4:table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(""+NumberFormattingg.threeDigitFor(reportData.get(b).getPetiontCount()));

                        break;
                    case 5:  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(""+NumberFormattingg.twoDigitPrecfloat(reportData.get(b).getNetAmount()));

                        pdfsum=pdfsum+reportData.get(b).getNetAmount();
                        Log.e("PDF SUM",""+pdfsum);
                    break;
                }


//        if (i%6 == 0) {
//            document.add(table);
//        }
//        table.addCell("Test " + i);
            }





        }
        for (i1 = 0; i1 < 6; i1++) {
            //  Log.d("",""+reportData.get(b).getClinicName()+ ""+reportData.get(b).getLevel1());
            switch (i1) {
                case 0:
                    table.addCell("");
                    break;
                case 1:
                    table.addCell("");
                    break;
                case 2:
                    table.addCell("");
                    break;
                case 3:
                    table.addCell("");
                    break;
                case 4:
                    table.addCell("Total");
                    break;
                case 5:  table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell("" +NumberFormattingg.twoDigitPrecfloat( pdfsum));
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

      /*  String mPath = Environment.getExternalStorageDirectory()+ "/" + "mygraphhhh" + ".jpg";
        // create bitmap screen capture
        View v1 = l1;
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







        Paragraph p2 = new Paragraph("Service Wise  Billing Report in graph",bold);
        p2.setAlignment(Paragraph.ALIGN_LEFT);
        p2.setSpacingAfter(20);

        document.add(p2);
        PdfPCell cell1 = new PdfPCell(image1, true);
        PdfPTable t1=new PdfPTable(1);
        t1.setWidthPercentage(100);
        t1.setTotalWidth(new float[]{ 500});
        t1.addCell(cell1);
        document.add(t1);
        document.close();
        startActivity(new Intent(this, MailSendingAcivity.class).putExtra("flag",getIntent().getExtras().getInt("flag")));

//        new UploadFileAsync().execute(myFile.getPath());
      //  (new Upload(ServiceWiseBillingReport.this, path)).execute();
      //  uploadFile();
    }


    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }
    //Requesting permission
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
             //   Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
              //  Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SelectActivity.reportData.clear();
       // Toast.makeText(this,"back pressed",Toast.LENGTH_LONG).show();
    }






    private class UploadFileAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                String sourceFileUri = params[0];

                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                File sourceFile = new File(sourceFileUri);

                if (sourceFile.isFile()) {

                    try {
                        String upLoadServerUri = "http://14.141.60.217/upload";

                        // open a URL connection to the Servlet
                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE",
                                "multipart/form-data");
                        conn.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("bill", sourceFileUri);

                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                                + sourceFileUri + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math
                                    .min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)
                       int  serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn
                                .getResponseMessage();

                        if (serverResponseCode == 200) {

                            // messageText.setText(msg);
                            //Toast.makeText(ctx, "File Upload Complete.",
                            //      Toast.LENGTH_SHORT).show();

                            // recursiveDelete(mDirectory1);

                        }

                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {

                        // dialog.dismiss();
                        e.printStackTrace();

                    }
                    // dialog.dismiss();

                } // End else block


            } catch (Exception ex) {
                // dialog.dismiss();

                ex.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
