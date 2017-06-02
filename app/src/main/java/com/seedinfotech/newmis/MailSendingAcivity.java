package com.seedinfotech.newmis;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import commonmodules.ProDailogs;
import okhttp3.RequestBody;

public class MailSendingAcivity extends AppCompatActivity {
int flag;
    EditText sendmail;
    File filepath;
    String Sub;
    EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
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
        sendmail=(EditText)findViewById(R.id.send_emailid);
        flag=getIntent().getExtras().getInt("flag");
       e=(EditText) findViewById(R.id.report_title);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();



        if(flag==1)
        {
            filepath=ServiceWiseBillingReport.myFile;
            e.setText("Service Wise Biling Report");
            Sub="Service Wise Biling Report";
        }
        if(flag==2)
        {
            filepath=PatientReportActivity.myFile;
            e.setText("Patient Registration Report");
            Sub="Patient Registration Report";
        }
        if(flag==3)
        {
            filepath=Revenue.myFile;
            e.setText("Revenue Report");
            Sub="Revenue Report";

        }

    }
    public void goback(View v)
    {
        onBackPressed();
    }
    public void sendEmailFinal(View v)
    {
      //  Toast.makeText(this,"called",Toast.LENGTH_LONG).show();
        if(DilogClasss.validate(sendmail.getText().toString())) {
            new UploadFileAsync().execute(filepath.toString());
        }
        else {
            sendmail.setError("please enter valid email address");
        }
    }
    private class UploadFileAsync extends AsyncTask<String, Void, String> {

        String Emaill;
        @Override
        protected void onPreExecute() {
            ProDailogs.showDailog(MailSendingAcivity.this);
            Emaill= sendmail.getText().toString();

        }
        @Override
        protected String doInBackground(String... params) {
            // String sourceFileUri = params[0];
            // String getresponse=readData(sourceFileUri);
            //  Log.e("vikas data",getresponse);

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
                        //   final String POST_PARAMS = "userName=Pankaj";

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


                        fileInputStream = new FileInputStream(sourceFile);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            //dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        }
                        dos = new DataOutputStream(conn.getOutputStream());
                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + sourceFileUri +"\"" + lineEnd);

                        dos.writeBytes("Content-Type:application/pdf" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.write(buffer);//your image array here buddy
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"email\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(Emaill);//your parameter value
                        dos.writeBytes(lineEnd); //to add multiple parameters write Content-Disposition: form-data; name=\"your parameter name\"" + crlf again and keep repeating till here :)
                        dos.writeBytes(twoHyphens + boundary);
                        dos.writeBytes("Content-Disposition: form-data; name=\"Sub\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(Sub);//your parameter value
                        dos.writeBytes(lineEnd); //to add multiple parameters write Content-Disposition: form-data; name=\"your parameter name\"" + crlf again and keep repeating till here :)
                        dos.writeBytes(twoHyphens + boundary + twoHyphens);

                        dos.flush();
                        dos.close();
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
            ProDailogs.dismissDailog(MailSendingAcivity.this);
            DilogClasss.diaplayDilog(getApplicationContext(),MailSendingAcivity.this);

        }



    }
}
