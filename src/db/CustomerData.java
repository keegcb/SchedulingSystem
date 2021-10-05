package db;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Customer;
import model.Division;

/**
 * class CustomerData.java
 * Holds prepared statements to interact with database when customer table or dependants are queried.
 */
public class CustomerData {
    /**
     * List of customers.
     */
    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * Gets all customers in database.
     * @return List of all customers in database.
     */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        Customer customer;
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM customers " +
                    "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID");
            while (result.next()){
                int id = result.getInt("Customer_ID");
                String name = result.getString("Customer_Name");
                String phone = result.getString("Phone");
                String address = result.getString("Address");
                String postal = result.getString("Postal_Code");
                String state = result.getString("Division");
                String country = result.getString("Country");
                int stateId = result.getInt("Division_ID");
                int countryId = result.getInt("Country_ID");

                customer = new Customer(id, name, address, postal, phone);
                customer.setCustState(state);
                customer.setCustCountry(country);
                customer.setStateId(stateId);
                customer.setCountryId(countryId);
                custList.add(customer);
            }
            query.close();
            return custList;
        }
        catch (SQLException e){
            System.out.println("The following SQL exception occurred:\n" + e.getMessage());
            return null;
        }
    }

    /**
     * Gets customer from database with given id.
     * @param id Id of customer to query.
     * @return Customer object of given customer.
     */
    public static Customer getCustomerById(int id) {
        Customer customer;

        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM customers WHERE Customer_ID = " + id);
            if(result.next()){
                int custId = result.getInt("Customer_ID");
                String custName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postal = result.getString("Postal_Code");
                String phone = result.getString("Phone");
                int custDivId = result.getInt("Division_ID");

                customer = new Customer(custId, custName, address, postal, phone);
                customer.setStateId(custDivId);
                query.close();
                return customer;
            }
        }
        catch (SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Gets next integer increment for customer id in database.
     * @return Integer value of next new customer id.
     */
    public static int getNextCustId(){
        int customerId;

        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT MAX(Customer_ID) " +
                    "AS Last_ID FROM customers");
            if(result.next()){
                customerId = result.getInt("Last_ID");
                customerId++;
                query.close();
                return customerId;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets division object for given division id.
     * @param divId Id of division to query.
     * @return Division object for given id.
     */
    public static Division getCustDiv(int divId){
        Division division;

        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM first_level_divisions WHERE Division_ID=" + divId);
            if(result.next()){
                int divisionId = result.getInt("Division_ID");
                String divName = result.getString("Division");
                int divCountryId = result.getInt("Country_ID");

                division = new Division(divisionId, divName, divCountryId);
                query.close();
                return division;
            }
        }
        catch(SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Gets country object for given country id.
     * @param cId Id of country to query.
     * @return Country object for given id.
     */
    public static Country getCustCountry(int cId){
        Country country;

        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM countries " +
                    "WHERE Country_ID=" + cId);
            if(result.next()){
                int countryId = result.getInt("Country_ID");
                String countryName = result.getString("Country");

                country = new Country(countryId, countryName);
                query.close();
                return country;
            }
        }
        catch (SQLException e){
            System.out.println("The following SQL Exception has occurred:\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Gets all countries in database.
     * @return List of all country objects in db.
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        Country country;

        try {
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM countries");
            while(result.next()){
                int countryId = result.getInt("Country_ID");
                String countryName = result.getString("Country");

                country = new Country(countryId, countryName);

                countryList.add(country);
            }
            query.close();
            return countryList;
        }
        catch(SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Gets all divisions in database.
     * @return List of all division objects in db.
     */
    public static ObservableList<Division> getAllDivisions(){
        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        Division division;

        try {
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM first_level_divisions");
            while(result.next()){
                int divisionId = result.getInt("Division_ID");
                String divisionName = result.getString("Division");
                int divCountryId = result.getInt("COUNTRY_ID");

                division = new Division(divisionId, divisionName, divCountryId);

                divisionList.add(division);
            }
            query.close();
            return divisionList;
        }
        catch(SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Gets country for object for given divisions country id.
     * @param cId Country id of division to query.
     * @return Country object for given divisions country id.
     */
    public static Country getCountryByDivision(int cId){
        Country country;

        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT DISTINCT countries.Country_ID, countries.Country FROM countries " +
                    "JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.COUNTRY_ID " +
                    "WHERE countries.Country_ID = " + cId);
            if(result.next()){
                int countryId = result.getInt("Country_ID");
                String countryName = result.getString("Country");

                country = new Country(countryId, countryName);
                query.close();
                return country;
            }
        }
        catch(SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Gets all divisions in database associated with given country.
     * @param id Id of country for division.
     * @return List of all divisions for given country.
     */
    public static ObservableList<Division> getDivisionByCountry(int id){
        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        Division division;

        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM first_level_divisions WHERE COUNTRY_ID=" + id);
            while(result.next()){
                int divisionId = result.getInt("Division_ID");
                String divisionName = result.getString("Division");
                int divCountryId = result.getInt("COUNTRY_ID");

                division = new Division(divisionId, divisionName, divCountryId);
                divisionList.add(division);
            }
            query.close();
            return divisionList;
        }
        catch(SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    /**
     * Attempts to insert new customer object data into customers table.
     * @param customer Customer object to insert.
     * @return True if customer was successfully added to db, false if customer not added.
     */
    public static boolean addCustomer(Customer customer){
        String sql = "INSERT INTO customers SET Customer_Name='" +
                customer.getCustName() + "', Address='" + customer.getCustAddress() + "', Postal_Code='" +
                customer.getCustPostal() + "', Phone='" + customer.getCustPhone() +
                "', Create_Date= NOW(), Created_By='" + UserData.getActiveUser().getUsername() +
                "', Last_Update=NOW(), Last_Updated_By='" +
                UserData.getActiveUser().getUsername() + "', Division_ID=" + customer.getStateId();
        try{
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException e) {
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return false;
    }

    /**
     * Attempts update on given customer in database.
     * @param customer Customer to update.
     * @return True if customer was successfully updated, false if customer not updated.
     */
    public static boolean updateCustomer(Customer customer){
        String sql = "UPDATE customers SET Customer_Name='" + customer.getCustName() +
                "', Address='" + customer.getCustAddress() + "', Postal_Code='" +
                customer.getCustPostal() + "', Phone='" + customer.getCustPhone() +
                "', Last_Update= NOW(), Last_Updated_By='" + UserData.getActiveUser().getUsername() +
                "', Division_ID=" + customer.getStateId() +
                " WHERE Customer_ID=" + customer.getCustId();
        try{
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException e) {
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return false;
    }

    /**
     * Attempts to delete given customer from database.
     * @param id Id of customer to delete.
     * @return True if customer successfully deleted, false if customer not deleted.
     */
    public static boolean deleteCustomer(int id){
        String sql = "DELETE FROM customers WHERE Customer_ID=" + id;
        try{
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            return true;
        }
        catch (SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return false;
    }
}
