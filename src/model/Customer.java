package model;

/**
 * class Customer.java
 * Customer class simulates customer person for appointment.
 */
public class Customer {
    /**
     * Id of customer.
     */
    private int custId;
    /**
     * Name of customer.
     */
    private String custName;
    /**
     * Address of customer.
     */
    private String custAddress;
    /**
     * Postal code of customer.
     */
    private String custPostal;
    /**
     * Phone number of customer.
     */
    private String custPhone;
    /**
     * Division name of customer.
     */
    private String custState;
    /**
     * Country name of customer.
     */
    private String custCountry;
    /**
     * Division id of customer.
     */
    private int stateId;
    /**
     * Country id of customer.
     */
    private int countryId;

    /**
     * Constructor initializes customer object with following parameters.
     * @param id Id of customer.
     * @param name Name of customer.
     * @param address Address of customer.
     * @param zip Postal code of customer.
     * @param phone Phone number of customer.
     */
    public Customer(int id, String name, String address, String zip, String phone) {
        custId = id;
        custName = name;
        custAddress = address;
        custPostal = zip;
        custPhone = phone;
    }

    /**
     * Default constructor initializes customer object with no parameters.
     */
    public Customer() {}

    /**
     * Customer name is printed value when changing object to string.
     * @return Name of customer.
     */
    @Override
    public String toString(){
        return (custName);
    }

    /**
     * Gets id of customer.
     * @return id to get.
     */
    public int getCustId(){
        return custId;
    }

    /**
     * Gets name of customer.
     * @return name to get.
     */
    public String getCustName(){
        return custName;
    }

    /**
     * Gets customer address.
     * @return address to get.
     */
    public String getCustAddress(){
        return custAddress;
    }

    /**
     * Gets postal code of customer.
     * @return postal code to get.
     */
    public String getCustPostal(){
        return custPostal;
    }

    /**
     * Gets phone number of customer.
     * @return phone number to get.
     */
    public String getCustPhone(){
        return custPhone;
    }

    /**
     * Gets Division name of customer.
     * @return division name to get.
     */
    public String getCustState(){ return custState; }

    /**
     * Gets Country name of customer.
     * @return country name to get.
     */
    public String getCustCountry(){return custCountry;}

    /**
     * Gets Division id of customer.
     * @return Division id to get.
     */
    public int getStateId(){return stateId;}

    /**
     * Gets Country id of customer.
     * @return Country id to get.
     */
    public int getCountryId(){return countryId;}

    /**
     * Sets id of customer.
     * @param id Id to set.
     */
    public void setCustId(int id){
        this.custId = id;
    }

    /**
     * Sets name of customer.
     * @param name Name to set.
     */
    public void setCustName(String name){
        this.custName = name;
    }

    /**
     * Sets address of customer.
     * @param address Address to set.
     */
    public void setCustAddress(String address){
        this.custAddress = address;
    }

    /**
     * Sets postal code of customer.
     * @param zip Postal code to set.
     */
    public void setCustPostal(String zip){
        this.custPostal = zip;
    }

    /**
     * Sets phone number of customer.
     * @param phone Phone number to set.
     */
    public void setCustPhone(String phone){
        this.custPhone = phone;
    }

    /**
     * Sets Division name of customer.
     * @param custState Division name to set.
     */
    public void setCustState(String custState) {
        this.custState = custState;
    }

    /**
     * Sets Country name of customer.
     * @param custCountry Country name to set.
     */
    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    /**
     * Sets Division id of customer.
     * @param stateId Division id to set.
     */
    public void setStateId(int stateId){this.stateId = stateId;}

    /**
     * Sets Country id of customer.
     * @param countryId Country id to set.
     */
    public void setCountryId(int countryId){this.countryId = countryId;}
}
