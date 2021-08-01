package db;

import java.sql.*;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

public class CustomerData {

    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    public static ObservableList<String> countryList = FXCollections.observableArrayList();
    public static ObservableList<String> divisionList = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        Customer customer;
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM customers " +
                    "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID");
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

    public Customer getCustomerById(int id) {
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM customers WHERE Customer_ID = '" + id +"'");
            if(result.next()){
                Customer qCust = new Customer();
                qCust.setCustName(result.getString("Customer_Name"));
                query.close();
                return qCust;
            }
        }
        catch (SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    public static int getCustomerByName(String name) {
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Customer_ID FROM customers WHERE Customer_Name = '" + name +"'");
            int custId = result.getInt("Customer_ID");
            return custId;
        }
        catch (SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return -1;
    }

    public static int getCustStateId(String division){
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Division_ID FROM first_level_divisions WHERE Division = '" + division + "'");
            int divisionId = result.getInt("Division_ID");
            query.close();
            return divisionId;
        }
        catch(SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return -1;
    }

    public static int getCustCountryId(int divisionId){
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Country_ID FROM countries " +
                    "JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID " +
                    "WHERE Division_ID = '" + divisionId + "'");
            int countryId = result.getInt("Division_ID");
            query.close();
            return countryId;
        }
        catch (SQLException e){
            System.out.println("The following SQL Exception has occurred:\n" + e.getMessage());
        }
        return -1;
    }

    public static ObservableList<String> getAllCountries(){
        try {
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Country FROM countries");
            while(result.next()){
                String countryName = result.getString("Country");

                countryList.add(countryName);
            }
            query.close();
            return countryList;
        }
        catch(SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    public static ObservableList<String> getAllDivisions(){
        try {
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Division FROM first_level_divisions");
            while(result.next()){
                String divisionName = result.getString("Division");

                divisionList.add(divisionName);
            }
            query.close();
            return divisionList;
        }
        catch(SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    public static String getCountryByDivision(String divName){
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Country FROM countries" +
                    "JOIN first_level_divisions ON country.Country_ID = first_level_divisions.Country_ID " +
                    "WHERE Division = '" + divName + "'");
            String countryName = result.getString("Country");
            query.close();
            return countryName;
        }
        catch(SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return null;
    }

    public static ObservableList<String> getDivisionByCountry(String cName){
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Division FROM first_level_divisions" +
                    "JOIN country ON first_level_divisions.Country_ID = country.Country_ID " +
                    "WHERE Country = '" + cName + "'");
            while(result.next()){
                String divisionName = result.getString("Division");

                divisionList.add(divisionName);
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
        try{
            Statement query = Database.getConnection().createStatement();
            query.executeQuery("INSERT INTO customers SET Customer_ID='" + customer.getCustId() + "', Customer_Name='" +
                    customer.getCustName() + "', Address='" + customer.getCustAddress() + "', Postal_Code='" +
                    customer.getCustPostal() + "', Phone='" + customer.getCustPhone() + "', Create_Date=" + LocalDateTime.now() +
                    ", Created_By='" + UserData.getActiveUser().getUsername() + "', Last_Update=" + LocalDateTime.now() + ", Last_Updated_By='" +
                    UserData.getActiveUser().getUsername() + "', Division_ID=" + customer.getStateId());
            return true;
        }
        catch (SQLException e) {
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return false;
    }

    public static boolean deleteCustomer(int id){
        try{
            Statement query = Database.getConnection().createStatement();
            query.executeQuery("DELETE FROM customer WHERE Customer_ID=" + id);
            return true;
        }
        catch (SQLException e){
            System.out.println("The following SQL Exception occurred:\n" + e.getMessage());
        }
        return false;
    }
}
