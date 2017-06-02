
package clinicpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Clinic implements Serializable{

    @SerializedName("Code")
    @Expose
    private String code;



    @SerializedName("Status")
    @Expose
    private boolean status;

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("UnitID")
    @Expose
    private Integer unitID;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public void setUnitID(Integer unitID) {
        this.unitID = unitID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  name ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}


