package clinicpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by commando3 on 5/5/2017.
 */

public class RevenuePojo implements Serializable {

    @SerializedName("Code")
    @Expose
    private String centerName;
    @SerializedName("BillType")
    @Expose
    private int billType;
    @SerializedName("NetBillAmount")
    @Expose
    private double netBillAmount;
    @SerializedName("PatientName")
    @Expose
    private String patientName;

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getCenterName() {
        return centerName;
    }

    @Override
    public String toString() {
        return "RevenuePojo{" +
                "billType=" + billType +
                ", centerName='" + centerName + '\'' +
                ", netBillAmount=" + netBillAmount +
                ", patientName='" + patientName + '\'' +
                '}';
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public double getNetBillAmount() {
        return netBillAmount;
    }

    public void setNetBillAmount(double netBillAmount) {
        this.netBillAmount = netBillAmount;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


}
