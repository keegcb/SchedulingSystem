package model;

/**
 * class Division.java
 * Division class simulates division of country
 */
public class Division {
    /**
     * Id of division.
     */
    private int divId;
    /**
     * Name of division.
     */
    private String divName;
    /**
     * Id of country for division.
     */
    private int divCountryId;

    /**
     * Constructor initializes division object with following parameters.
     * @param id Id of division.
     * @param name Name of division.
     * @param cid Id of country for division.
     */
    public Division(int id, String name, int cid){
        divId = id;
        divName = name;
        divCountryId = cid;
    }

    /**
     * Default constructor initializes division object with no parameters.
     */
    public Division(){}

    /**
     * Division name is printed value when changing object to string.
     * @return Name of Division.
     */
    @Override
    public String toString(){
        return divName;
    }

    /**
     * Gets id of division.
     * @return Id to get.
     */
    public int getDivId() {
        return divId;
    }

    /**
     * Gets name of division.
     * @return division name to get.
     */
    public String getDivName() {
        return divName;
    }

    /**
     * Gets Country id of Division.
     * @return country id to get.
     */
    public int getDivCountryId() {
        return divCountryId;
    }

    /**
     * Sets id of division.
     * @param divId division id to set.
     */
    public void setDivId(int divId) {
        this.divId = divId;
    }

    /**
     * Sets name of division.
     * @param divName division name to set.
     */
    public void setDivName(String divName) {
        this.divName = divName;
    }

    /**
     * Sets country id of division.
     * @param divCountryId Country id to set.
     */
    public void setDivCountryId(int divCountryId) {
        this.divCountryId = divCountryId;
    }
}
