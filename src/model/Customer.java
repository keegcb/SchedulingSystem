package model;

public class Customer {

    private int custId;
    private String custName;
    private String custAddress;
    private String custZip;
    private String custPhone;

    public Customer(int id, String name, String address, String zip, String phone) {
        custId = id;
        custName = name;
        custAddress = address;
        custZip = zip;
        custPhone = phone;
    }

    public Customer() {}

    public int getCustId(){
        return custId;
    }

    public String getCustName(){
        return custName;
    }

    public String getCustAddress(){
        return custAddress;
    }

    public String getCustZip(){
        return custZip;
    }

    public String getCustPhone(){
        return custPhone;
    }

    public void setCustId(int id){
        custId = id;
    }

    public void setCustName(String name){
        custName = name;
    }

    public void setCustAddress(String address){
        custAddress = address;
    }

    public void setCustZip(String zip){
        custZip = zip;
    }

    public void setCustPhone(String phone){
        custPhone = phone;
    }
}
