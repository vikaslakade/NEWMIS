package clinicpojo;

/**
 * Created by commando3 on 5/11/2017.
 */

public class PataintCountDashBoard {
    String Level4;
    int patientCount;

    public PataintCountDashBoard(String level4, int patientCount) {
        Level4 = level4;
        this.patientCount = patientCount;
    }

    public String getLevel4() {
        return Level4;
    }

    @Override
    public String toString() {
        return "PataintCountDashBoard{" +
                "Level4='" + Level4 + '\'' +
                ", patientCount=" + patientCount +
                '}';
    }

    public void setLevel4(String level4) {
        Level4 = level4;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }
}
