package db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.concurrent.SynchronousQueue;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Profile;

public class CustomerData {

    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        Customer customer;
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM customers " +
                    "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID");
            while (result.next() == true){
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
            if(result.next() == true){
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

    public String getCountryName(int countryId){
        try{
            Statement query = Database.getConnection().createStatement();
            ResultSet result = query.executeQuery("SELECT Country FROM countries WHERE Country_ID = '" + countryId + "'");
            String country = result.getString("Country");
            query.close();
            return country;
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
                    ", Created_By='" + Profile.getUsername() + "', Last_Update=" + LocalDateTime.now() + ", Last_Updated_By='" +
                    Profile.getUsername() + "', Division_ID=" + customer.getStateId());
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
