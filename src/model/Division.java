package model;

public class Division {

    private int divId;
    private String divName;
    private int divCountryId;

    public Division(int id, String name, int cid){
        divId = id;
        divName = name;
        divCountryId = cid;
    }

    public Division(){}

    @Override
    public String toString(){
        return divName;
    }

    public int getDivId() {
        return divId;
    }

    public String getDivName() {
        return divName;
    }

    public int getDivCountryId() {
        return divCountryId;
    }

    public void setDivId(int divId) {
        this.divId = divId;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }

    public void setDivCountryId(int divCountryId) {
        this.divCountryId = divCountryId;
    }
}
