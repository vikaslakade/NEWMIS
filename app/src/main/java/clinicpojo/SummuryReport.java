package clinicpojo;

/**
 * Created by commando3 on 4/20/2017.
 */

public class SummuryReport {

    String ClinicName;
    String Level1;
    String Level2;
    int ServiceCount;
    int PetiontCount;
    double NetAmount;

    public SummuryReport(String level1, String level2, String clinicName, double netAmount, int petiontCount, int serviceCount) {
        Level1 = level1;
        Level2 = level2;
        ClinicName = clinicName;
        NetAmount = netAmount;
        PetiontCount = petiontCount;
        ServiceCount = serviceCount;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
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

    public int getPetiontCount() {
        return PetiontCount;
    }

    public void setPetiontCount(int petiontCount) {
        PetiontCount = petiontCount;
    }

    public int getServiceCount() {
        return ServiceCount;
    }

    @Override
    public String toString() {
        return "SummuryReport{" +
                "ClinicName='" + ClinicName + '\'' +
                ", Level1='" + Level1 + '\'' +
                ", Level2='" + Level2 + '\'' +
                ", ServiceCount=" + ServiceCount +
                ", PetiontCount=" + PetiontCount +
                ", NetAmount=" + NetAmount +
                '}';
    }

    public void setServiceCount(int serviceCount) {
        ServiceCount = serviceCount;
    }
}
