package clinicpojo;

/**
 * Created by commando3 on 5/23/2017.
 */

public class ClinicWiseRevenue {
    int revenueSum;
    String unitCode;

    public ClinicWiseRevenue(int revenueSum, String unitCode) {
        this.revenueSum = revenueSum;
        this.unitCode = unitCode;
    }

    public double getRevenueSum() {
        return revenueSum;
    }

    public void setRevenueSum(int revenueSum) {
        this.revenueSum = revenueSum;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
}
