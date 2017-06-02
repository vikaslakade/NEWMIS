package com.seedinfotech.newmis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by commando2 on 2017-05-25.
 */

public class DilogClasss {
    public static void  diaplayDilog(Context ctx, final Activity activity)
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogue_box);

       TextView textdate = (TextView) dialog.findViewById(R.id.textView_date);
        textdate.setText(dataformat());

        TextView texttime = (TextView) dialog.findViewById(R.id.textView_time);
        texttime.setText(timeformat());

       Button dialogButton = (Button) dialog.findViewById(R.id.button_dailog);
       dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
            }
        });

        dialog.show();

    }


    public static String dataformat(){

        SimpleDateFormat simpleDate = new SimpleDateFormat("EEEE dd MMM yyyy");
        Date date = new Date();
        String S = simpleDate.format(date);

        return S;

    }
    public static String timeformat(){

        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm a");
        Date date = new Date();
        String S = simpleDate.format(date);

        return S;

    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }


}
