package clinicpojo;

/**
 * Created by commando3 on 5/11/2017.
 */

public class RevenueDetailspojo {
    String Clinic;
    double Amt;
    String BillType;




    public RevenueDetailspojo( String clinic,double amt, String billType) {
        Amt = amt;
        BillType = billType;
        Clinic = clinic;
    }

    public String getBillType() {
        return BillType;
    }

    public void setBillType(String billType) {
        BillType = billType;
    }

    public double getAmt() {
        return Amt;

    }
    public void setAmt(double amt) {
        Amt = amt;
    }

    public String getClinic() {
        return Clinic;
    }

    public void setClinic(String clinic) {
        Clinic = clinic;
    }

    @Override
    public String toString() {
        return "RevenueDetailspojo{" +
                "Amt=" + Amt +
                ", Clinic='" + Clinic + '\'' +
                ", BillType='" + BillType + '\'' +
                '}';
    }
}
