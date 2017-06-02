package clinicpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by commando3 on 5/3/2017.
 */

public class RegistrationPojo implements Serializable {
    @Override
    public String toString() {
        return "RegistrationPojo{" +
                "clinicName='" + clinicName + '\'' +
                ", regType='" + regType + '\'' +
                ", regFrom=" + regFrom +
                ", patientName='" + patientName + '\'' +
                '}';
    }

    @SerializedName("ClinicName")
    @Expose
    private String clinicName;
    @SerializedName("RegType")
    @Expose
    private String regType;

    @SerializedName("RegFrom")
    @Expose
    private Integer regFrom;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public int getRegFrom() {
        return regFrom;
    }

    public void setRegFrom(int regFrom) {
        this.regFrom = regFrom;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    @Expose
    @SerializedName("PatientName")

    private String patientName;


}
