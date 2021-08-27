package model;

public class Customer {

    private int custId;
    private String custName;
    private String custAddress;
    private String custPostal;
    private String custPhone;
    private String custState;
    private String custCountry;
    private int stateId;
    private int countryId;

    public Customer(int id, String name, String address, String zip, String phone) {
        custId = id;
        custName = name;
        custAddress = address;
        custPostal = zip;
        custPhone = phone;
    }

    public Customer() {}

    @Override
    public String toString(){
        return (custName);
    }

    public int getCustId(){
        return custId;
    }

    public String getCustName(){
        return custName;
    }

    public String getCustAddress(){
        return custAddress;
    }

    public String getCustPostal(){
        return custPostal;
    }

    public String getCustPhone(){
        return custPhone;
    }

    public String getCustState(){ return custState; }

    public String getCustCountry(){return custCountry;}

    public int getStateId(){return stateId;}

    public int getCountryId(){return countryId;}

    public void setCustId(int id){
        this.custId = id;
    }

    public void setCustName(String name){
        this.custName = name;
    }

    public void setCustAddress(String address){
        this.custAddress = address;
    }

    public void setCustPostal(String zip){
        this.custPostal = zip;
    }

    public void setCustPhone(String phone){
        this.custPhone = phone;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public void setStateId(int stateId){this.stateId = stateId;}

    public void setCountryId(int countryId){this.countryId = countryId;}
}
