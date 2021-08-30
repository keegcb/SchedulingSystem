package db;

import java.sql.*;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Customer;
import model.Division;
import model.User;

public class CustomerData {

    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();

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
