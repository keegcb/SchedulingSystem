package model;

/**
 * class Country.java
 * Country class simulates country identity
 */
public class Country {

    /**
     * Id of country.
     */
    private int cid;
    /**
     * Name of country.
     */
    private String cName;

    /**
     * Constructor initializes Country object with following parameters.
     * @param id Id of country
     * @param name Name of country
     */
    public Country(int id, String name){
        cid = id;
        cName = name;
    }

    /**
     * Default constructor initializes Country object with no parameters.
     */
    public Country(){}

    /**
     * Country name is printed value when changing object to String.
     * @return name of country
     */
    @Override
    public String toString(){
        return cName;
    }

    /**
     * Gets id of country.
     * @return id to get
     */
    public int getCid() {
        return cid;
    }

    /**
     * Sets id of country.
     * @param cid Id to set.
     */
    public void setCid(int cid) {
        this.cid = cid;
    }

    /**
     * Gets name of country.
     * @return name to get.
     */
    public String getcName() {
        return cName;
    }

    /**
     * Sets name of country.
     * @param cName name to set.
     */
    public void setcName(String cName) {
        this.cName = cName;
    }
}
