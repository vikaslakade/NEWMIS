package clinicpojo;

/**
 * Created by commando3 on 4/19/2017.
 */

public class Finalmodelcalss {

     String Level1;
     String Level2;
     Double NetAmount;
    String  ServiceName;
    String PatientName;
    String ClinicName;

    public String getPatientName() {
        return PatientName;
    }

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public Finalmodelcalss(String level1, String level2, Double netAmount, String serviceName, String patientName, String clinicName) {
        Level1 = level1;
        Level2 = level2;
        NetAmount = netAmount;
        ServiceName = serviceName;
        PatientName=patientName;
        ClinicName=clinicName;


    }

    public String getLevel1() {
        return Level1;

    }

    public void setLevel1(String level1) {
        Level1 = level1;
    }

    public String getLevel2() {
        return Level2;
    }

    public void setLevel2(String level2) {
        Level2 = level2;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public String getServiceName() {
        return ServiceName;
    }

    @Override
    public String toString() {
        return "Finalmodelcalss{" +
                "ClinicName='" + ClinicName + '\'' +
                ", Level1='" + Level1 + '\'' +
                ", Level2='" + Level2 + '\'' +
                ", NetAmount=" + NetAmount +
                ", ServiceName='" + ServiceName + '\'' +
                ", PatientName='" + PatientName + '\'' +
                '}';
    }

    public void setServiceName(String serviceName) {
        this.ServiceName = serviceName;
    }



    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }
}
