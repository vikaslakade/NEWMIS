package clinicpojo;

import java.util.HashSet;

/**
 * Created by commando3 on 5/5/2017.
 */

public class RevenueMap {

    public static HashSet<String> centerName;
    public static HashSet<String> patientName;
    public static HashSet<Integer> BillType;
    public static HashSet<Double>netBillAmount;

    public static HashSet<Integer> getBillType() {
        return BillType;
    }

    public static void setBillType(HashSet<Integer> billType) {
        BillType = billType;
    }

    public static HashSet<String> getCenterName() {
        return centerName;
    }

    public static void setCenterName(HashSet<String> centerName) {
        RevenueMap.centerName = centerName;
    }

    public static HashSet<Double> getNetBillAmount() {
        return netBillAmount;
    }

    public static void setNetBillAmount(HashSet<Double> netBillAmount) {
        RevenueMap.netBillAmount = netBillAmount;
    }

    public static HashSet<String> getPatientName() {
        return patientName;
    }

    public static void setPatientName(HashSet<String> patientName) {
        RevenueMap.patientName = patientName;
    }
}
