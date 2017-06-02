package clinicpojo;

/**
 * Created by commando3 on 5/4/2017.
 */

public class Patient_Reg_Final  {
   String ClinicName;
     String RegForm;
    String RegType;
    int count;

    public Patient_Reg_Final(String clinicName, int count, String regForm, String regType) {
        ClinicName = clinicName;
        this.count = count;
        RegForm = regForm;
        RegType = regType;
    }

    @Override
    public String toString() {
        return "Patient_Reg_Final{" +
                "ClinicName='" + ClinicName + '\'' +
                ", RegForm='" + RegForm + '\'' +
                ", RegType='" + RegType + '\'' +
                ", count=" + count +
                '}';
    }

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRegForm() {
        return RegForm;
    }

    public void setRegForm(String regForm) {
        RegForm = regForm;
    }

    public String getRegType() {
        return RegType;
    }

    public void setRegType(String regType) {
        RegType = regType;
    }


}
