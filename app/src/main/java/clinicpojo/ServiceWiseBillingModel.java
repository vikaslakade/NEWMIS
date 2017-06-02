package clinicpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by commando3 on 4/17/2017.
 */

public class ServiceWiseBillingModel implements Serializable
{


    @SerializedName("PatientName")
    @Expose
    private String patientName;
    @SerializedName("MRNO")
    @Expose
    private String mRNO;
    @SerializedName("Level1")
    @Expose
    private String level1;
    @SerializedName("Level2")
    @Expose
    private String level2;




    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("TotalAmount")
    @Expose
    private Integer totalAmount;

    @SerializedName("NetBillAmount")
    @Expose
    private Double netBillAmount;



    @SerializedName("ClinicName")
    @Expose
    private String clinicName;




    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMRNO() {
        return mRNO;
    }

    public void setMRNO(String mRNO) {
        this.mRNO = mRNO;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }













    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }



    public Double getNetBillAmount() {
        return netBillAmount;
    }

    public void setNetBillAmount(Double netBillAmount) {
        this.netBillAmount = netBillAmount;
    }







    @Override
    public String toString() {
        return "ServiceWiseBillingModel{" +

                ", patientName='" + patientName + '\'' +
                ", mRNO='" + mRNO + '\'' +
                ", level1='" + level1 + '\'' +
                ", level2='" + level2 + '\'' +

                ", serviceName='" + serviceName + '\'' +
                ", totalAmount=" + totalAmount +

                ", netBillAmount=" + netBillAmount +

                ", clinicName='" + clinicName + '\'' +
                '}';
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

}
