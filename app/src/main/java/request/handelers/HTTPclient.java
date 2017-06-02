package request.handelers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by commando3 on 4/17/2017.
 */

public class HTTPclient {


    public  static  String getData(String url)
    {
        StringBuilder sb = new StringBuilder();
        try {
            URL url1=new URL(url);
            HttpURLConnection conn=(HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            InputStream ip=conn.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(ip));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    ip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
    public  static  String PostData(String url,String data)
    {
        StringBuilder sb = new StringBuilder();
        try {
            URL url1=new URL(url);
            HttpURLConnection conn=(HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");

            conn.setDoInput(true);
            conn.connect();
            OutputStream op=conn.getOutputStream();
            Log.d("data to post",""+data);
            op.write(data.getBytes());
            op.close();
            InputStream ip=conn.getInputStream();


            BufferedReader br=new BufferedReader(new InputStreamReader(ip));


            String line;
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line).append('\n');
                    System.out.println(sb.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    ip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
