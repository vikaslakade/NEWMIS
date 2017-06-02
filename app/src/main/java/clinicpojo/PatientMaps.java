package clinicpojo;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by commando3 on 5/3/2017.
 */

public class PatientMaps {

  public static HashSet<String> ClinicName;
    public static HashSet<String> Regstation_Type;
    public static HashSet<Integer> Reg_form;

    public HashSet<String> getClinicName() {
        return ClinicName;
    }

    public void setClinicName(HashSet<String> clinicName) {
        ClinicName = clinicName;
    }

    public HashSet<Integer> getReg_form() {
        return Reg_form;
    }

    public void setReg_form(HashSet<Integer> reg_form) {
        Reg_form = reg_form;
    }

    public HashSet<String> getRegstation_Type() {
        return Regstation_Type;
    }

    public void setRegstation_Type(HashSet<String> regstation_Type) {
        Regstation_Type = regstation_Type;
    }
}
