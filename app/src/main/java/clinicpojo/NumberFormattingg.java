package clinicpojo;

import java.text.DecimalFormat;

/**
 * Created by commando2 on 2017-05-26.
 */

public class NumberFormattingg {

    public static  String threeDigitFor(int num)
    {
        String formatted = String.format("%03d", num);
        return formatted;
    }
    public static String twoDigitPrecfloat(double  num)
    {
        //DecimalFormat formatter = new DecimalFormat("##,##,###.00");
        String formato = String.format("%.2f",num);
        return formato;
    }

}
