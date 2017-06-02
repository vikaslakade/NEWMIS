package commonmodules;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by commando3 on 5/19/2017.
 */

public class ProDailogs {
   static ProgressDialog progressDialog;

    public static ProgressDialog showDailog(Context context){
        progressDialog=new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        return progressDialog;
    }

    public static ProgressDialog dismissDailog(Context context){
       if(progressDialog.isShowing()) {
           progressDialog.dismiss();
       }
        return progressDialog;
    }
}
