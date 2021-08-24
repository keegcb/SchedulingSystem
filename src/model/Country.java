package model;

public class Country {

    private int cid;
    private String cName;

    public Country(int id, String name){
        cid = id;
        cName = name;
    }

    public Country(){}

    @Override
    public String toString(){
        return cName;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
}
