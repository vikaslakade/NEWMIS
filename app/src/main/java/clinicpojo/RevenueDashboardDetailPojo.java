package clinicpojo;

/**
 * Created by commando3 on 5/12/2017.
 */

public class RevenueDashboardDetailPojo {
    String ClinicNAme;
    String BillType;
    double NetAmt;

    public RevenueDashboardDetailPojo(String billType, String clinicNAme, double netAmt) {
        BillType = billType;
        ClinicNAme = clinicNAme;
        NetAmt = netAmt;
    }

    public String getBillType() {
        return BillType;
    }

    public void setBillType(String billType) {
        BillType = billType;
    }

    public String getClinicNAme() {
        return ClinicNAme;
    }

    public void setClinicNAme(String clinicNAme) {
        ClinicNAme = clinicNAme;
    }

    public double getNetAmt() {
        return NetAmt;
    }

    public void setNetAmt(double netAmt) {
        NetAmt = netAmt;
    }
}
