package clinicpojo;

import java.util.HashSet;

/**
 * Created by commando3 on 4/19/2017.
 */

public  class Myhashset {

  static  public HashSet<String> Level1;
    static public  HashSet<String> Level2;
    static  public HashSet<String> Services;
    static public HashSet<String> petiont_name;
    static public HashSet<String> clinicName;

    public static HashSet<String> getClinicName() {
        return clinicName;
    }

    public static void setClinicName(HashSet<String> clinicName) {
        Myhashset.clinicName = clinicName;
    }

    public HashSet<String> getLevel1() {
        return Level1;
    }

    public void setLevel1(HashSet<String> level1) {
        Level1 = level1;
    }

    @Override
    public String toString() {
        return "Myhashset{" +
                "Level1=" + Level1 +
                ", Level2=" + Level2 +
                ", Services=" + Services +
                ", petiont_name=" + petiont_name +
                '}';
    }

    public HashSet<String> getLevel2() {
        return Level2;
    }

    public void setLevel2(HashSet<String> level2) {
        Level2 = level2;
    }

    public HashSet<String> getPetiont_name() {
        return petiont_name;
    }

    public void setPetiont_name(HashSet<String> petiont_name) {
        this.petiont_name = petiont_name;
    }

    public HashSet<String> getServices() {
        return Services;
    }

    public void setServices(HashSet<String> services) {
        Services = services;
    }
}
