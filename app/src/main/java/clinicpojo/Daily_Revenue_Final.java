package clinicpojo;

/**
 * Created by commando3 on 5/5/2017.
 */

public class Daily_Revenue_Final  {

    String ClinicName;
    String BillCategory;
    double NetAmount;
    int PatientCount;

    public Daily_Revenue_Final(String billCategory, String clinicName, double netAmount, int patientCount) {
        BillCategory = billCategory;
        ClinicName = clinicName;
        NetAmount = netAmount;
        PatientCount = patientCount;
    }

    public String getBillCategory() {
        return BillCategory;
    }

    public void setBillCategory(String billCategory) {
        BillCategory = billCategory;
    }

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }

    @Override
    public String toString() {
        return "Daily_Revenue_Final{" +
                "BillCategory='" + BillCategory + '\'' +
                ", ClinicName='" + ClinicName + '\'' +
                ", NetAmount=" + NetAmount +
                ", PatientCount=" + PatientCount +
                '}';
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }

    public int getPatientCount() {
        return PatientCount;
    }

    public void setPatientCount(int patientCount) {
        PatientCount = patientCount;
    }
}
